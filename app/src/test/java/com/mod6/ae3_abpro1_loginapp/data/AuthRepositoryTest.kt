package com.mod6.ae3_abpro1_loginapp.data

import com.mod6.ae3_abpro1_loginapp.data.AuthRepository
import com.mod6.ae3_abpro1_loginapp.data.AuthService
import com.mod6.ae3_abpro1_loginapp.data.LoginRequest
import com.mod6.ae3_abpro1_loginapp.data.TokenResponse
import com.mod6.ae3_abpro1_loginapp.domain.AuthState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class AuthRepositoryTest {

    private lateinit var authService: AuthService
    private lateinit var repository: AuthRepository

    @Before
    fun setup() {
        authService = mockk()
        repository = AuthRepository(authService)
    }

    @Test
    fun `login correcto retorna token`() = runTest {
        val token = "abc.def.ghi"
        coEvery { authService.login(LoginRequest("mor_2314",
            "83r5^_")) } returns TokenResponse(token)

        val result = repository.login("mor_2314", "83r5^_")

        assertTrue(result is AuthState.Success)
        assertEquals(token, (result as AuthState.Success).token)
    }

    @Test
    fun `login incorrecto devuelve error`() = runTest {
        val httpException = HttpException(Response.error<Any>(400, "".toResponseBody()))
        coEvery { authService.login(LoginRequest("bad",
            "bad")) } throws httpException

        val result = repository.login("bad", "bad")

        assertTrue(result is AuthState.Error)
        assertEquals("Credenciales inválidas", (result as AuthState.Error).message)
    }

    @Test
    fun `falla de red devuelve error de red`() = runTest {
        coEvery { authService.login(any<LoginRequest>()) } throws IOException("Network error")

        val result = repository.login("x", "y")

        assertTrue(result is AuthState.NetworkError)
    }
}