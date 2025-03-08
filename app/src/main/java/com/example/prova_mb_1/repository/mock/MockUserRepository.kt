package com.example.prova_mb_1.repository.mock

import com.example.prova_mb_1.model.User
import com.example.prova_mb_1.repository.UserRepository

/**
 * Class that implements the [UserRepository] interface for testing and data simulation.
 *
 * This class provides a test implementation for obtaining user data.
 *
 * @author [Alex Saborit, assaborit@gmail.com]
 */
class MockUserRepository : UserRepository {
    override suspend fun getUsers(): List<User> {
        return listOf(
            User(1, "Mock User 1", "mock1@example.com", ""),
            User(2, "Mock User 2", "mock2@example.com", "")
        )
    }
}
//TODO("IF APP IS USED OFFLINE, CRASHES. THIS CLASS IS NOT USED??")