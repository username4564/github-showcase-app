package com.example.showcaseapp.data.auth

import android.content.Context
import androidx.core.content.edit
import com.example.showcaseapp.data.auth.GitHubSecretConstants.clientId
import com.example.showcaseapp.data.auth.GitHubSecretConstants.clientSecret
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class AuthRepository(context: Context) {
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

    private val sharedPreference = context
        .getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)

    suspend fun getAuthToken(code: String): String {
        return retrofit
            .create<GitHubService>()
            .getAuthToken(clientId = clientId, clientSecret = clientSecret, code = code)
            .accessToken
    }

    suspend fun setAuthToken(token: String) {
        sharedPreference.edit(commit = true) {
            putString(keyAuthToken, token)
        }
    }

    companion object {
        private const val sharedPreferencesName = "Preferences"

        private const val keyAuthToken = "AuthToken"
    }
}

