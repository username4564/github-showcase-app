package com.example.showcaseapp.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.example.showcaseapp.utils.StoreFactoryServiceLocator
import kotlinx.coroutines.flow.filterIsInstance

@Composable
internal fun AuthScreen(goToNext: () -> Unit = {}) {
    // TODO: ET 31.05.2022 add store state holder?
    val rememberStore = remember {
        mutableStateOf(value = StoreFactoryServiceLocator.getAuthStore())
    }
    val store = rememberStore.value

    val label = store
        .labels
        .filterIsInstance<AuthStore.Label>()
        .collectAsState(initial = Unit)
    when (label.value) {
        is AuthStore.Label.GoToNext -> goToNext()
        else -> Unit
    }

    val state = store
        .states
        .filterIsInstance<AuthStore.State>()
        .collectAsState(initial = Unit)
    when (state.value) {
        is AuthStore.State -> AuthScreenContent()
        else -> Unit
    }
}

@Composable
internal fun AuthScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
        )
    }
}

@Preview(
    name = "Auth screen",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun AuthScreenPreview() = AuthScreenContent()