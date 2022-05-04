package com.example.showcaseapp

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.utils.JvmSerializable
import com.example.showcaseapp.WelcomeStore.Intent
import com.example.showcaseapp.WelcomeStore.Label
import com.example.showcaseapp.WelcomeStore.State

internal interface WelcomeStore : Store<Intent, State, Label> {

    // Serializable only for exporting events in Time Travel, no need otherwise.
    sealed class Intent : JvmSerializable {
        object Auth : Intent()
        object Terms : Intent()
    }

    // Serializable only for exporting events in Time Travel, no need otherwise.
    object State : JvmSerializable

    // Serializable only for exporting events in Time Travel, no need otherwise.
    sealed class Label : JvmSerializable {
        object GoToWebAuth : Label()
        object GoToTerms : Label()
    }

}