package com.example.prova_mb_1.repository

import com.example.prova_mb_1.model.User

interface UserRepository {
    suspend fun getUsers(): List<User>
}