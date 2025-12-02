package com.mod6.ae3_abpro1_loginapp.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mod6.ae3_abpro1_loginapp.data.AuthRepository
import com.mod6.ae3_abpro1_loginapp.domain.AuthState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var repository: AuthRepository
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = LoginViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `campos vacíos retorna error`() = runTest {
        viewModel.login("", "")
        val state = viewModel.loginResult.value
        assertTrue(state is AuthState.Error)
        assertEquals("Usuario y contraseña son obligatorios",
            (state as AuthState.Error).message)
    }

    @Test
    fun `login exitoso devuelve token`() = runTest {
        val token = "fakeToken123"
        coEvery { repository.login("mor_2314",
            "83r5^") } returns AuthState.Success(token)

        viewModel.login("mor_2314", "83r5^")
        runCurrent()

        val state = viewModel.loginResult.value
        assertTrue(state is AuthState.Success)
        assertEquals(token, (state as AuthState.Success).token)
    }

    @Test
    fun `credenciales erróneas retorna error`() = runTest {
        val errorMessage = "Credenciales inválidas. Por favor, verifica tu usuario y contraseña."
        coEvery { repository.login("bad",
            "bad") } returns AuthState.Error(errorMessage)

        viewModel.login("bad", "bad")
        runCurrent()

        val state = viewModel.loginResult.value
        assertTrue(state is AuthState.Error)
        assertEquals(errorMessage, (state as AuthState.Error).message)
    }

    @Test
    fun `error de red retorna error de red`() = runTest {
        coEvery { repository.login("mor_2314",
            "83r5^") } returns AuthState.NetworkError

        viewModel.login("mor_2314", "83r5^")
        runCurrent()

        val state = viewModel.loginResult.value
        assertTrue(state is AuthState.NetworkError)
    }
}