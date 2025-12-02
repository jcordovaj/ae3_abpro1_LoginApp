package com.mod6.ae3_abpro1_loginapp.domain

sealed class AuthState {
    data class Success(val token: String) : AuthState()
    data class Error(val message: String) : AuthState()
    object NetworkError : AuthState()
    object Loading : AuthState()
    object Idle : AuthState()
}