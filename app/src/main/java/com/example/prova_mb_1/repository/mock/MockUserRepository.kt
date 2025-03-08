package com.example.prova_mb_1.repository.mock

import com.example.prova_mb_1.model.User
import com.example.prova_mb_1.repository.UserRepository

class MockUserRepository : UserRepository {
    override suspend fun getUsers(): List<User> {
        // Retorna una llista d'usuaris simulats
        return listOf(
            User(1, "Mock User 1", "mock1@example.com", ""),
            User(2, "Mock User 2", "mock2@example.com", "")
        )
    }
}