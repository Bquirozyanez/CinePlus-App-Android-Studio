package com.example.cineplus.data.remote

import com.example.cineplus.data.remote.dto.LoginRequest
import com.example.cineplus.data.remote.dto.LoginResponse
import com.example.cineplus.data.remote.dto.RegisterRequest
import com.example.cineplus.data.remote.dto.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface ApiService {

    // ====== AUTENTICACIÓN ======
    //Y Apuntes pal read.me

    /**
     * Endpoint de LOGIN
     * Inicia sesión con email y contraseña.
     * Retorna un token de autenticación si los datos son correctos.
     */
    @POST("auth/login")
    suspend fun loginUser(
        @Body request: LoginRequest
    ): LoginResponse


    /**
     * Endpoint de REGISTRO
     * Crea un nuevo usuario en el sistema y devuelve su token.
     */
    @POST("auth/signup")
    suspend fun registerUser(
        @Body request: RegisterRequest
    ): RegisterResponse


    /**
     * Endpoint para obtener los datos del usuario actual.
     * Requiere el token en el header "Authorization".
     */
    @GET("auth/me")
    suspend fun getUserData(
        @Header("Authorization") token: String
    ): RegisterResponse
}
