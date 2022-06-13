package com.example.showcaseapp

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.core.utils.JvmSerializable
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.example.showcaseapp.WelcomeStore.Intent
import com.example.showcaseapp.WelcomeStore.Label
import com.example.showcaseapp.WelcomeStore.State
import kotlin.coroutines.CoroutineContext

internal class WelcomeStoreFactory(
    private val storeFactory: StoreFactory,
    private val mainContext: CoroutineContext,
) {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): WelcomeStore =
        object : WelcomeStore,
            Store<Intent, State, Label> by storeFactory.create<Intent, Unit, Msg, State, Label>(
                name = "AuthStore",
                initialState = State,
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = coroutineExecutorFactory(mainContext) {
                    onIntent<Intent.Auth> {
                        publish(Label.GoToWebAuth)
                    }
                    onIntent<Intent.Terms> {
                        publish(Label.GoToTerms)
                    }
                },
                reducer = { throw NotImplementedError("Its not required!") },
            ) {}


    // Serializable only for exporting events in Time Travel, no need otherwise.
    private sealed class Msg : JvmSerializable

}