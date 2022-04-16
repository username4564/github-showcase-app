package com.example.showcaseapp.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.showcaseapp.R
import com.example.showcaseapp.ui.theme.textColorSecondary

@Composable
fun AuthScreen() {
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
            onClick = { /*TODO*/ },
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
        Text(
            text = stringResource(id = R.string.auth_terms),
            color = MaterialTheme.textColorSecondary,
            style = MaterialTheme.typography.body2,
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
    }
}

@Preview(
    name = "Экран авторизации",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun AuthScreenPreview() = AuthScreen()