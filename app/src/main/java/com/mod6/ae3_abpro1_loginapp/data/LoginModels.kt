package com.mod6.ae3_abpro1_loginapp.data

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    val username: String,
    val password: String
)

data class TokenResponse(
    @SerializedName("token")
    val token: String
)