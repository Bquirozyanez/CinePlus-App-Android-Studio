package com.example.cineplus.repository

import com.example.cineplus.data.remote.RetrofitClient
import com.example.cineplus.data.remote.dto.LoginRequest
import com.example.cineplus.data.remote.dto.LoginResponse
import com.example.cineplus.data.remote.dto.RegisterRequest
import com.example.cineplus.data.remote.dto.RegisterResponse


class AuthRepository {

    private val api = RetrofitClient.apiService

    // ====== LOGIN ======
    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val request = LoginRequest(email, password)
            val response = api.loginUser(request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ====== REGISTRO ======
    suspend fun register(email: String, password: String, name: String?): Result<RegisterResponse> {
        return try {
            val request = RegisterRequest(email, password, name)
            val response = api.registerUser(request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ====== OBTENER DATOS DEL USUARIO ======
    suspend fun getUserData(token: String): Result<RegisterResponse> {
        return try {
            val response = api.getUserData("Bearer $token")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
