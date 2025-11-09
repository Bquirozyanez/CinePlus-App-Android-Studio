package com.example.cineplus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cineplus.repository.AuthRepository
import com.example.cineplus.data.remote.dto.RegisterResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val repository = AuthRepository()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    //resultado exitoso del registro (true/false)
    private val _isSuccess = MutableStateFlow<Boolean?>(null)
    val isSuccess: StateFlow<Boolean?> = _isSuccess

    // token recibido
    private val _authToken = MutableStateFlow<String?>(null)
    val authToken: StateFlow<String?> = _authToken

    private val _response = MutableStateFlow<RegisterResponse?>(null)
    val response: StateFlow<RegisterResponse?> = _response

    // mensaje de error (para mostrar en la UI)
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    /**
     * Apunte pal read.me:
     * Llama al repositorio para registrar un usuario con la API de Xano.
     * La UI debería llamar a esta función cuando el usuario presione "Registrarse".
     */
    fun register(email: String, password: String, name: String?) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _isSuccess.value = null

            val result = repository.register(email, password, name)

            result
                .onSuccess { registerResponse ->
                    _response.value = registerResponse

                    // Si llego un token, se guarda en el estado
                    _authToken.value = registerResponse.authToken

                    //se marca exito si hubo token o id o al menos no vino error
                    _isSuccess.value =
                        !registerResponse.authToken.isNullOrEmpty() ||
                                registerResponse.id != null

                    if (_isSuccess.value == false && !registerResponse.message.isNullOrBlank()) {
                        _error.value = registerResponse.message
                    }
                }
                .onFailure { e ->
                    _isSuccess.value = false
                    _error.value = e.localizedMessage ?: "Error al registrar usuario"
                }

            _isLoading.value = false
        }
    }

    /**
     * Apunte pal read.me:
     * Permite resetear el estado (por ejemplo, después de mostrar un mensaje
     * o al salir de la pantalla).
     */
    fun resetState() {
        _isSuccess.value = null
        _error.value = null
        _response.value = null
    }
}
