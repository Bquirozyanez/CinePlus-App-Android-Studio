package com.example.cineplus.data.remote.dto

data class LoginResponse(
    val authToken: String?,     // token de autenticacion que devuelve el servidor
    val message: String?        // mensaje opcional de exito o error
)
