package com.dicoding.picodiploma.loginwithanimation.data

import com.dicoding.picodiploma.loginwithanimation.data.response.RegisterResponse
import com.dicoding.picodiploma.loginwithanimation.data.retrofit.ApiService

class Repository(private val apiService: ApiService) {
    suspend fun registerUser (name: String, email: String, password: String): RegisterResponse {
        return apiService.register(name, email, password)
    }
}