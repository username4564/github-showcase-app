package com.example.showcaseapp.data.auth

import com.example.showcaseapp.data.auth.GitHubSecretConstants.clientId
import com.example.showcaseapp.data.auth.GitHubSecretConstants.clientSecret
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class AuthRepository {
    private val okHttpClient = run {
        val logging = HttpLoggingInterceptor().also {
            it.setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        client
    }
    private val gson = Gson()
    private val retrofit = Retrofit
        .Builder()
        .baseUrl("https://api.github.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    suspend fun getAuthToken(code: String): String {
        return retrofit
            .create<GitHubService>()
            .getAuthToken(clientId = clientId, clientSecret = clientSecret, code = code)
            .accessToken
    }

    suspend fun setAuthToken(authToken: String) {
        // TODO: ET 13.06.2022 !
    }
}

