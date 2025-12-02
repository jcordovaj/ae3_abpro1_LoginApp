package com.mod6.ae3_abpro1_loginapp.data

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): TokenResponse
}