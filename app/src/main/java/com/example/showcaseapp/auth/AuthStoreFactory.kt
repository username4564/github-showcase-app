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
import kotlin.coroutines.CoroutineContext

// TODO: ET 01.06.2022 ! 
// TODO: ET 02.06.2022 register app 
// TODO: ET 02.06.2022 add web view open link 
//https://github.com/login/oauth/authorize?scope=user:email&client_id=<%= client_id %>"
// TODO: ET 02.06.2022 wait callback 
// TODO: ET 02.06.2022 send auth request
internal class AuthStoreFactory(
    private val storeFactory: StoreFactory,
    private val mainContext: CoroutineContext,
) {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): AuthStore =
        object : AuthStore,
            Store<Intent, State, Label> by storeFactory.create<Intent, Unit, Msg, State, Label>(
                name = "AuthStore",
                initialState = State,
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = coroutineExecutorFactory(mainContext) {
                    onIntent<Intent.Auth> {
                        publish(Label.GoToNext)
                    }
                    onIntent<Intent.Terms> {
                        TODO()
                    }
                },
                reducer = { throw NotImplementedError("Its not required!") },
            ) {}


    // Serializable only for exporting events in Time Travel, no need otherwise.
    private sealed class Msg : JvmSerializable

}