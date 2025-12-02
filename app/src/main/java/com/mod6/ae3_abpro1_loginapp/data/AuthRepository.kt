package com.mod6.ae3_abpro1_loginapp.data

import com.mod6.ae3_abpro1_loginapp.domain.AuthState

class AuthRepository(private val api: AuthService) {

    suspend fun login(username: String, password: String): AuthState {
        try {
            // Auditoría
            android.util.Log.d("AuthRepoDBG",
                "login: username='$username' password='$password' -> llamando API")
            val response = api.login(LoginRequest(username, password))
            android.util.Log.d("AuthRepoDBG", "login: Retrofit devolvió: $response")

            // Si response es TokenResponse normal
            if (response.token.isNotEmpty()) {
                android.util.Log.d("AuthRepoDBG", "login: token recibido")
                return AuthState.Success(response.token)
            } else {
                android.util.Log.d("AuthRepoDBG", "login: token vacío en body")
                return AuthState.Error("Token no recibido")
            }
        } catch (e: retrofit2.HttpException) {
            val code = e.code()
            val body = try { e.response()?.errorBody()?.string() } catch (ex: Exception) { null }
            android.util.Log.d("AuthRepoDBG", "HttpException code=$code body=$body")
            return AuthState.Error("Alguno de los datos es incorrecto, vuelva a intentar")
        } catch (e: java.io.IOException) {
            android.util.Log.d("AuthRepoDBG", "IOException: ${e.message}")
            return AuthState.NetworkError
        } catch (e: Exception) {
            android.util.Log.d("AuthRepoDBG", "Exception: ${e.javaClass}: ${e.message}")
            return AuthState.Error("Error desconocido")
        }
    }
}
