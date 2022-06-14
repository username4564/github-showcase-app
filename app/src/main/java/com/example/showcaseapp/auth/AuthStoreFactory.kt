package com.example.showcaseapp.auth

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.core.utils.JvmSerializable
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutorScope
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.example.showcaseapp.auth.AuthStore.Intent
import com.example.showcaseapp.auth.AuthStore.Label
import com.example.showcaseapp.auth.AuthStore.State
import com.example.showcaseapp.domain.auth.AuthResultError
import com.example.showcaseapp.domain.auth.AuthResultSuccess
import com.example.showcaseapp.domain.auth.AuthResultUnexpectedUrl
import com.example.showcaseapp.domain.auth.GetAuthToken
import com.example.showcaseapp.domain.auth.GetAuthUrl
import com.example.showcaseapp.domain.auth.GetAuthUrlResult
import com.example.showcaseapp.domain.auth.SetAuthToken
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

internal class AuthStoreFactory(
    private val getAuthUrl: GetAuthUrl,
    private val getAuthUrlResult: GetAuthUrlResult,
    private val getAuthToken: GetAuthToken,
    private val setAuthToken: SetAuthToken,
    private val storeFactory: StoreFactory,
    private val ioContext: CoroutineContext,
    private val mainContext: CoroutineContext,
) {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): AuthStore =
        object : AuthStore,
            Store<Intent, State, Label> by storeFactory.create<Intent, Unit, Msg, State, Label>(
                name = "AuthStore",
                initialState = State.Content(getAuthUrl()),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = coroutineExecutorFactory(mainContext) {
                    var authStage: AuthStage = AuthStage.WebLogin

                    fun CoroutineExecutorScope<Msg, State, Label>.authRequest(code: String) {
                        // TODO: ET 14.06.2022 replace try catch and launch with
                        //  Deferred and Either<Result, Error>?
                        try {
                            launch {
                                dispatch(Msg.Loading)
                                withContext(ioContext) {
                                    val authToken = getAuthToken(code)
                                    setAuthToken(authToken)
                                }
                                publish(Label.GoToNext)
                            }
                        } catch (e: Exception) {
                            authStage = AuthStage.AuthRequest(code)
                            dispatch(Msg.Error)
                        }
                    }

                    onIntent<Intent.WebUrlRequested> {
                        when (val result = getAuthUrlResult(it.url)) {
                            is AuthResultError -> {
                                authStage = AuthStage.WebLogin
                                dispatch(Msg.Error)
                            }
                            is AuthResultSuccess -> authRequest(result.code)
                            is AuthResultUnexpectedUrl -> Unit
                        }
                    }
                    onIntent<Intent.Retry> {
                        when (val stage = authStage) {
                            is AuthStage.WebLogin -> dispatch(Msg.Content(getAuthUrl()))
                            is AuthStage.AuthRequest -> authRequest(stage.code)
                        }
                    }
                },
                reducer = { msg ->
                    when (msg) {
                        is Msg.Content -> State.Content(msg.authUrl)
                        is Msg.Loading -> State.Loading
                        is Msg.Error -> State.Error
                    }
                },
            ) {}

    // Serializable only for exporting events in Time Travel, no need otherwise.
    private sealed class Msg : JvmSerializable {
        class Content(val authUrl: String) : Msg()
        object Loading : Msg()
        object Error : Msg()
    }

    private sealed class AuthStage {
        object WebLogin : AuthStage()
        class AuthRequest(val code: String) : AuthStage()
    }

}