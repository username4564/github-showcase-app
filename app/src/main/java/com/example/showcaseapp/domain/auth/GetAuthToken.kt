package com.example.showcaseapp.domain.auth

import com.example.showcaseapp.data.auth.AuthRepository

class GetAuthToken(
    private val AuthRepository: AuthRepository,
) {
    suspend operator fun invoke(code: String): String = AuthRepository.getAuthToken(code)
}