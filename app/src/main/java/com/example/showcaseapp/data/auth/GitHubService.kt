package com.example.showcaseapp.data.auth

import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface GitHubService {
    @POST("https://github.com/login/oauth/access_token")
    @Headers(
        "Accept: application/json",
    )
    suspend fun getAuthToken(
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("code") code: String
    ): AuthTokenResponse
}