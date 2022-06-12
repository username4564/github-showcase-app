package com.example.showcaseapp.auth

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.core.utils.JvmSerializable
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.example.showcaseapp.auth.AuthStore.Intent
import com.example.showcaseapp.auth.AuthStore.Label
import com.example.showcaseapp.auth.AuthStore.State
import com.example.showcaseapp.domain.auth.AuthResultError
import com.example.showcaseapp.domain.auth.AuthResultSuccess
import com.example.showcaseapp.domain.auth.AuthResultUnexpectedUrl
import com.example.showcaseapp.domain.auth.GetAuthUrl
import com.example.showcaseapp.domain.auth.GetAuthUrlResult
import kotlin.coroutines.CoroutineContext

// TODO: ET 01.06.2022 !
// TODO: ET 02.06.2022 send auth request
internal class AuthStoreFactory(
    private val getAuthUrl: GetAuthUrl,
    private val getAuthUrlResult: GetAuthUrlResult,
    private val storeFactory: StoreFactory,
    private val mainContext: CoroutineContext,
) {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): AuthStore =
        object : AuthStore,
            Store<Intent, State, Label> by storeFactory.create<Intent, Unit, Msg, State, Label>(
                name = "AuthStore",
                initialState = State.Content(getAuthUrl.invoke()),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = coroutineExecutorFactory(mainContext) {
                    onIntent<Intent.OnUrlRequested> {
                        when (getAuthUrlResult(it.url)) {
                            is AuthResultError -> TODO()
                            // TODO: ET 12.06.2022 add auth request
                            is AuthResultSuccess -> publish(Label.GoToNext)
                            is AuthResultUnexpectedUrl -> Unit
                        }
                    }
                },
                reducer = { throw NotImplementedError("Its not required!") },
            ) {}


    // Serializable only for exporting events in Time Travel, no need otherwise.
    private sealed class Msg : JvmSerializable

}