package com.example.showcaseapp.auth

import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.example.showcaseapp.auth.AuthStore.Intent
import com.example.showcaseapp.auth.AuthStore.Label
import com.example.showcaseapp.auth.AuthStore.State
import com.example.showcaseapp.utils.ContentError
import com.example.showcaseapp.utils.ContentLoading
import com.example.showcaseapp.utils.StoreFactoryServiceLocator
import com.example.showcaseapp.utils.subscribeToStore

@Composable
internal fun AuthScreen(goToNext: () -> Unit) = subscribeToStore(
    factory = {
        StoreFactoryServiceLocator.getAuthStore()
    },
    labels = {
        when (it) {
            is Label.GoToNext -> goToNext()
            null -> Unit
        }
    },
    states = { state, store ->
        when (state) {
            is State.Content -> AuthScreenContent(
                authUrl = state.authUrl,
                onUrlLoading = { store.accept(Intent.WebUrlRequested(it)) }
            )
            State.Loading -> ContentLoading()
            State.Error -> ContentError(onRetry = { store.accept(Intent.Retry) })
            null -> Unit
        }
    }
)

@Composable
internal fun AuthScreenContent(authUrl: String, onUrlLoading: (String) -> Unit) {
    AndroidView(
        factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                settings.javaScriptEnabled = true
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest
                    ): Boolean {
                        onUrlLoading(request.url.toString())
                        return false
                    }
                }

                loadUrl(authUrl)
                // TODO: ET 12.06.2022 process loading|content callbacks
            }
        },
        update = { },
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
    )
}

@Preview(
    name = "Auth screen",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun AuthScreenPreview() = AuthScreenContent(authUrl = "", onUrlLoading = {})