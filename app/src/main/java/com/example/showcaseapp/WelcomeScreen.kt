package com.example.showcaseapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.showcaseapp.WelcomeStore.Intent
import com.example.showcaseapp.ui.theme.textColorSecondary
import com.example.showcaseapp.utils.Compose
import com.example.showcaseapp.utils.LogCompositions
import com.example.showcaseapp.utils.StoreFactoryServiceLocator
import com.example.showcaseapp.utils.subscribeToStore

@Composable
internal fun WelcomeScreen(
    goToAuth: () -> Unit,
    goToTerms: () -> Unit
) = subscribeToStore(
    factory = {
        StoreFactoryServiceLocator.getWelcomeStore()
    },
    labels = {
        when (it) {
            is WelcomeStore.Label.GoToWebAuth -> goToAuth()
            is WelcomeStore.Label.GoToTerms -> goToTerms()
            null -> Unit
        }
    },
    states = { state, store ->
        when (state) {
            is WelcomeStore.State -> WelcomeScreenContent(
                onAuthClick = { store.accept(Intent.Auth) },
                onTermsClick = { store.accept(Intent.Terms) },
            )
        }
    }
)

@Composable
internal fun WelcomeScreenContent(
    onAuthClick: () -> Unit,
    onTermsClick: () -> Unit,
) {
    LogCompositions("TAG", "AuthScreen")
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
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_github_logo),
            contentDescription = Compose.CONTENT_DESCRIPTION_STUB,
            modifier = Modifier
                .width(156.dp)
                .height(156.dp)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(156.dp)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
        )
        Button(
            onClick = { onAuthClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = stringResource(id = R.string.auth_authorize))
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
        ClickableText(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = MaterialTheme.textColorSecondary)) {
                    append(stringResource(id = R.string.auth_terms))
                }
            },
            style = MaterialTheme.typography.body2,
            onClick = { onTermsClick() }
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
    }
}

@Preview(
    name = "Welcome screen",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun WelcomeScreenPreview() = WelcomeScreenContent(
    onAuthClick = {},
    onTermsClick = {},
)