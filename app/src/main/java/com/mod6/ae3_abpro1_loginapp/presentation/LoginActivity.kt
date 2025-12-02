package com.mod6.ae3_abpro1_loginapp.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mod6.ae3_abpro1_loginapp.data.AuthRepository
import com.mod6.ae3_abpro1_loginapp.data.RetrofitClient
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import android.widget.ProgressBar
import android.widget.TextView
import com.mod6.ae3_abpro1_loginapp.R
import com.mod6.ae3_abpro1_loginapp.domain.AuthState

// Factory simple para instanciar el ViewModel con dependencias.
class LoginViewModelFactory(private val repository: AuthRepository) : ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var etUsername: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: MaterialButton
    private lateinit var progressBar: ProgressBar
    private lateinit var tvMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicializa vistas
        etUsername  = findViewById(R.id.editTextUsername)
        etPassword  = findViewById(R.id.editTextPassword)
        btnLogin    = findViewById(R.id.buttonLogin)
        progressBar = findViewById(R.id.progressBar)
        tvMessage   = findViewById(R.id.textViewMessage)

        // Inicializa ViewModel
        val repository = AuthRepository(RetrofitClient.authService)
        viewModel = ViewModelProvider(this,
            LoginViewModelFactory(repository)).get(LoginViewModel::class.java)

        setupObservers()

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            viewModel.login(username, password)
        }
    }

    private fun setupObservers() {
        // Observador para el estado de carga
        viewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            btnLogin.isEnabled = !isLoading

            // Muestra mensaje de error cuando isLoading pasa de true -> false
            if (isLoading) {
                tvMessage.visibility = View.GONE
            }
        }

        // Observador para el resultado del login
        viewModel.loginResult.observe(this) { state ->
            when (state) {
                is AuthState.Success -> handleSuccess(state.token)
                is AuthState.Error -> handleError(state.message)
                AuthState.NetworkError -> handleError("Fallo de la red. Revisa tu conexión.")
                AuthState.Loading -> { progressBar.visibility = View.VISIBLE }
                AuthState.Idle -> {}
            }
        }
    }

    private fun handleSuccess(token: String) {
        // La API de Fakestore no devuelve el nombre de usuario, sólo token
        // Para la demo, mostramos en pantalla el nombre de usuario ingresado (mor_2314)
        val username = etUsername.text.toString()
        val intent = Intent(this,
            SplashFinalActivity::class.java).apply {
            putExtra("USERNAME_KEY", username)
            putExtra("TOKEN_KEY", token)
        }
        startActivity(intent)
        finish()
    }

    private fun handleError(message: String) {
        tvMessage.text = message
        tvMessage.setTextColor(getColor(android.R.color.holo_red_dark))
        tvMessage.visibility = View.VISIBLE
    }

    private fun handleNetworkError() {
        val msg = "Fallo de red. Por favor, revisa tu conexión."
        tvMessage.text = msg
        tvMessage.setTextColor(getColor(android.R.color.holo_red_dark))
        tvMessage.visibility = View.VISIBLE
    }
}