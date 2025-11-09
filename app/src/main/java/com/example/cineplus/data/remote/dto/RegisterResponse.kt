package com.example.cineplus.data.remote.dto


data class RegisterResponse(
    val authToken: String?,   // token de autenticación que devuelve Xano
    val id: Int?,             // id del usuario creado
    val email: String?,       // correo del usuario
    val name: String? = null, // nombre del usuario
    val message: String? = null // mensaje informativo o de error
)
