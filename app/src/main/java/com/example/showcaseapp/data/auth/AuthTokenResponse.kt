package com.example.showcaseapp.data.auth

import com.google.gson.annotations.SerializedName

class AuthTokenResponse(
    @SerializedName("access_token")
    val accessToken: String
)