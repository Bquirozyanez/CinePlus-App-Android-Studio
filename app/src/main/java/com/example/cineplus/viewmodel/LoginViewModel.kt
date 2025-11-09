package com.example.cineplus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cineplus.repository.AuthRepository
import com.example.cineplus.data.remote.dto.LoginResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val repository = AuthRepository()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isSuccess = MutableStateFlow<Boolean?>(null)
    val isSuccess: StateFlow<Boolean?> = _isSuccess

    private val _authToken = MutableStateFlow<String?>(null)
    val authToken: StateFlow<String?> = _authToken

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _isSuccess.value = null

            val result = repository.login(email, password)

            result
                .onSuccess { loginResponse ->
                    _authToken.value = loginResponse.authToken
                    _isSuccess.value = !loginResponse.authToken.isNullOrEmpty()

                    if (_isSuccess.value == false && !loginResponse.message.isNullOrBlank()) {
                        _error.value = loginResponse.message
                    }
                }
                .onFailure { e ->
                    _isSuccess.value = false
                    _error.value = e.localizedMessage ?: "Error al iniciar sesión"
                }

            _isLoading.value = false
        }
    }

    fun resetState() {
        _isSuccess.value = null
        _error.value = null
    }
}
