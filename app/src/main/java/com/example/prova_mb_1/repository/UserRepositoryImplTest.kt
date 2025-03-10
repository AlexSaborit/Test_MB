package com.example.prova_mb_1.repository

import com.example.prova_mb_1.api.ApiService
import com.example.prova_mb_1.model.User
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class UserRepositoryImplTest {
    private lateinit var apiService: ApiService
    private lateinit var userRepositoryImpl: UserRepositoryImpl

    @Before
    fun setup() {
        apiService = mockk()
        userRepositoryImpl = UserRepositoryImpl(apiService)
    }

    @Test
    fun `getUsers - success`() = runTest {
        // Given
        val users = listOf(User(1, "John Doe", "john@example.com", ""))
        coEvery { apiService.getUsers() } returns Response.success(users)

        // When
        val result = userRepositoryImpl.getUsers()

        // Then
        assertEquals(users, result)
    }

    @Test
    fun `getUsers - empty`() = runTest {
        // Given
        val users = emptyList<User>()
        coEvery { apiService.getUsers() } returns Response.success(users)

        // When
        val result = userRepositoryImpl.getUsers()

        // Then
        assertEquals(users, result)
    }

    @Test
    fun `getUsers - error`() = runTest {
        // Given
        coEvery { apiService.getUsers() } returns Response.error(404, "Not Found".toResponseBody())

        // When
        val result = userRepositoryImpl.getUsers()

        // Then
        assertEquals(emptyList<User>(), result)
    }
}