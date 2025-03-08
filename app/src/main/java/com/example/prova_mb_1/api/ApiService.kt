package com.example.prova_mb_1.api

import com.example.prova_mb_1.model.User
import retrofit2.Response
import retrofit2.http.GET

// Retrofit interface for the API
interface ApiService {
    @GET("users")
    suspend fun getUsers(): Response<List<User>>
}
