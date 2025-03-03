package com.example.prova_mb_1.api

import com.example.prova_mb_1.User
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/*// Model de dades
data class User(
    val id: Int,
    val name: String,
    val email: String
)*/

// Retrofit interface for the API
interface UserService {
    @GET("users")
    suspend fun getUsers(): List<User>
}

// Retrofit Client (Singleton)
object RetrofitClient {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    val userService: UserService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserService::class.java)
    }
}
