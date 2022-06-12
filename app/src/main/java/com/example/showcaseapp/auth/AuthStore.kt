package com.example.showcaseapp.auth

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.utils.JvmSerializable
import com.example.showcaseapp.auth.AuthStore.Intent
import com.example.showcaseapp.auth.AuthStore.Label
import com.example.showcaseapp.auth.AuthStore.State

internal interface AuthStore : Store<Intent, State, Label> {

    // Serializable only for exporting events in Time Travel, no need otherwise.
    sealed class Intent : JvmSerializable {
        class OnUrlRequested(val url: String) : Intent()
    }

    // Serializable only for exporting events in Time Travel, no need otherwise.
    sealed class State : JvmSerializable {
        class Content(val authUrl: String) : State()
        object Loading : State()
    }

    // Serializable only for exporting events in Time Travel, no need otherwise.
    sealed class Label : JvmSerializable {
        object GoToNext : Label()
    }

}