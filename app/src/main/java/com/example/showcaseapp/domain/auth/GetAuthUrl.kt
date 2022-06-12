package com.example.showcaseapp.domain.auth

import com.example.showcaseapp.data.auth.GitHubSecretConstants.clientId

class GetAuthUrl {
    // TODO: ET 12.06.2022 move it to repository
    private val authUrl =
        "https://github.com/login/oauth/authorize?scope=user:email&client_id=${clientId}"

    operator fun invoke(): String = authUrl
}