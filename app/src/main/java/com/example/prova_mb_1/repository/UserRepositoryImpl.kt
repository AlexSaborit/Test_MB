package com.example.prova_mb_1.repository

import com.example.prova_mb_1.api.ApiService
import com.example.prova_mb_1.model.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : UserRepository {
    override suspend fun getUsers(): List<User> {
        val response = apiService.getUsers()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            // Error management. Returns an empty list in case of error.
            return emptyList()
        }
    }
}