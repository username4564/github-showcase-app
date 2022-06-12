package com.example.showcaseapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.states
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

// TODO: ET 12.06.2022 its scratch
@Composable
fun <Intent : Any, State : Any, Label : Any> rememberStore(
    factory: () -> Store<Intent, State, Label>,
): Store<Intent, State, Label> {
    val rememberStore = remember { mutableStateOf(value = factory()) }
    return rememberStore.value
}

// TODO: ET 12.06.2022 its scratch
@Composable
inline fun <reified Intent : Any, reified State : Any, reified Label : Any> subscribeToStore(
    crossinline factory: () -> Store<Intent, State, Label>,
    states: @Composable (State?, Store<Intent, State, Label>) -> Unit,
    labels: @Composable (Label?) -> Unit,
) {
    val store = rememberStore { factory() }
    states(store.subscribeToStatesValue(), store)
    labels(store.subscribeToLabelsValue())
}

// TODO: ET 12.06.2022 its scratch
@Composable
inline fun <reified T : Any> Store<*, *, T>.subscribeToLabels(
    context: CoroutineContext = EmptyCoroutineContext
): State<T?> {
    val result = remember { mutableStateOf(null as T?) }
    LaunchedEffect(this, context) {
        launch {
            labels
                .filterIsInstance<T?>()
                .filter { it != null }
                .collectLatest {
                    result.value = it
                    // TODO: ET 12.06.2022 WA ULTRA SCRATCH
                    delay(10)
                    result.value = null
                }
        }
    }
    return result
}

// TODO: ET 12.06.2022 its scratch
@Composable
inline fun <reified T : Any> Store<*, *, T>.subscribeToLabelsValue(): T? = subscribeToLabels().value

// TODO: ET 12.06.2022 its scratch
@Composable
inline fun <reified T : Any> Store<*, T, *>.subscribeToStates(
    context: CoroutineContext = EmptyCoroutineContext
): State<T?> {
    val result = remember { mutableStateOf(null as T?) }
    LaunchedEffect(this, context) {
        launch {
            states
                .filterIsInstance<T?>()
                .filter { it != null }
                .collectLatest {
                    result.value = it
                }
        }
    }
    return result
}

// TODO: ET 12.06.2022 its scratch
@Composable
inline fun <reified T : Any> Store<*, T, *>.subscribeToStatesValue(): T? = subscribeToStates().value