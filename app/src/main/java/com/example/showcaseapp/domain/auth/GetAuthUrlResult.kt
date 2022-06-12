package com.example.showcaseapp.domain.auth

import com.example.showcaseapp.data.auth.GitHubSecretConstants.authCallbackUrl

class GetAuthUrlResult { //UseCase

    object AuthCallbackParams {
        const val code = "code"
        const val errorDescription = "error_description"
    }

    // TODO: ET 12.06.2022 move it to repository
    // TODO: ET 12.06.2022 process auth
    // https://showcaseapp/auth?code=123
    // TODO: ET 12.06.2022 process error
    // https://showcaseapp/auth?error=access_denied&error_description=The+user+has+denied+your+application+access.&error_uri=https%3A%2F%2Fdocs.github.com%2Fapps%2Fmanaging-oauth-apps%2Ftroubleshooting-authorization-request-errors%2F%23access-denied
    operator fun invoke(authUrl: String): AuthResult {
        if (authUrl.startsWith(authCallbackUrl, ignoreCase = true).not()) {
            return AuthResultUnexpectedUrl
        }
        if (authUrl.contains(AuthCallbackParams.code, ignoreCase = true)) {
            val code = getParam(authUrl, AuthCallbackParams.code)
            return AuthResultSuccess(code)
        }
        if (authUrl.contains("error", ignoreCase = true)) {
            val message = getParam(authUrl, AuthCallbackParams.errorDescription)
            return AuthResultError(message)
        }
        throw UnsupportedOperationException("Unexpected auth url: $authUrl")
    }

    // TODO: ET 12.06.2022 move it to repository
    private fun getParam(url: String, param: String): String {
        return url
            .substringAfter("?")
            .split("&")
            .first { it.startsWith(param) }
            .split("=")
            .component2()
    }
}