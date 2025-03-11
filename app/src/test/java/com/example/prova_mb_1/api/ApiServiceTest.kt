package com.example.prova_mb_1.api

import com.example.prova_mb_1.model.User
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class ApiServiceTest {
    private lateinit var apiService: ApiService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getUsers should request users from correct endpoint`() = runBlocking {
        // Arrange
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("[]")
        mockWebServer.enqueue(mockResponse)

        // Act
        apiService.getUsers()

        // Assert
        val request = mockWebServer.takeRequest()
        assertEquals("/users", request.path)
        assertEquals("GET", request.method)
    }

    @Test
    fun `getUsers should return list of users when successful`() = runBlocking {
        // Arrange
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(
                """
                    [
                        {
                            "id": 1,
                            "name": "User 1",
                            "username": "user1",
                            "email": "user1@example.com",
                            "phone": "123456789",
                            "website": "www.user1.com"
                        },
                        {
                            "id": 2,
                            "name": "User 2",
                            "username": "user2",
                            "email": "user2@example.com",
                            "phone": "987654321",
                            "website": "www.user2.com"
                        }
                    ]
                """.trimIndent()
            )
        mockWebServer.enqueue(mockResponse)

        // Act
        val response = apiService.getUsers()

        // Assert
        assertEquals(HttpURLConnection.HTTP_OK, response.code())
        val users = response.body()!!
        assertEquals(2, users.size)
        assertEquals(User(1, "User 1", "user1", "user1@example.com", "123456789", "www.user1.com"), users[0])
        assertEquals(User(2, "User 2", "user2", "user2@example.com", "987654321", "www.user2.com"), users[1])
    }

    @Test
    fun `getUsers should handle server error`() = runBlocking {
        // Arrange
        val mockResponse = MockResponse().setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
        mockWebServer.enqueue(mockResponse)

        // Act
        val response = apiService.getUsers()

        // Assert
        assertEquals(HttpURLConnection.HTTP_INTERNAL_ERROR, response.code())
    }

    @Test
    fun `getUsers should handle connection error`() = runBlocking {
        // Arrange
        mockWebServer.shutdown()

        // Act
        try {
            apiService.getUsers()
        } catch (e: Exception) {
            // Assert
            assert(e is java.net.ConnectException)
        }
    }
}