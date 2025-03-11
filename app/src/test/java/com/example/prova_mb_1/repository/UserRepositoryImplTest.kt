package com.example.prova_mb_1.repository

import com.example.prova_mb_1.api.ApiService
import com.example.prova_mb_1.model.User
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

class UserRepositoryImplTest {
    private val apiService: ApiService = mockk()
    private val userRepository = UserRepositoryImpl(apiService)

    @Test
    fun `getUsers should call apiService getUsers`() = runTest {
        // Arrange
        coEvery { apiService.getUsers() } returns Response.success(emptyList())

        // Act
        userRepository.getUsers()

        // Assert
        coVerify(exactly = 1) { apiService.getUsers() }
    }

    @Test
    fun `getUsers should return list of users when response is successful`() = runTest {
        // Arrange
        val users = listOf(User(1, "User 1"))
        coEvery { apiService.getUsers() } returns Response.success(users)

        // Act
        val result = userRepository.getUsers()

        // Assert
        assertEquals(users, result)
    }

    @Test
    fun `getUsers should return empty list when response body is null`() = runTest {
        // Arrange
        coEvery { apiService.getUsers() } returns Response.success(null)

        // Act
        val result = userRepository.getUsers()

        // Assert
        assertEquals(emptyList<User>(), result)
    }

    @Test
    fun `getUsers should return empty list when response is not successful`() = runTest {
        // Arrange
        coEvery { apiService.getUsers() } returns Response.error(404, mockk(relaxed = true))

        // Act
        val result = userRepository.getUsers()

        // Assert
        assertEquals(emptyList<User>(), result)
    }
}