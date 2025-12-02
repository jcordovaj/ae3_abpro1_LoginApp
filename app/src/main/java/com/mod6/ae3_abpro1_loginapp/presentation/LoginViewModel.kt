package com.mod6.ae3_abpro1_loginapp.presentation

import androidx.lifecycle.*
import com.mod6.ae3_abpro1_loginapp.data.AuthRepository
import com.mod6.ae3_abpro1_loginapp.domain.AuthState
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<AuthState>(AuthState.Idle)
    val loginResult: LiveData<AuthState> = _loginResult

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            _loginResult.value = AuthState.Error("Usuario y contraseña son obligatorios")
            return
        }

        _isLoading.value = true
        _loginResult.value = AuthState.Loading

        viewModelScope.launch {
            val result = repository.login(username, password)
            _loginResult.value = result
            _isLoading.value = false
        }
    }

    fun resetResult() {
        _loginResult.value = AuthState.Idle
    }
}