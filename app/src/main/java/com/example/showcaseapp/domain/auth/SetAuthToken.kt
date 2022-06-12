package com.example.showcaseapp.domain.auth

import com.example.showcaseapp.data.auth.AuthRepository

class SetAuthToken(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(token: String) = authRepository.setAuthToken(token)
}