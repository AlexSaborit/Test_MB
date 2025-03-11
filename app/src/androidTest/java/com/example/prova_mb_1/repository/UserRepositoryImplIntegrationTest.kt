package com.example.prova_mb_1.repository

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.prova_mb_1.api.ApiService
import com.example.prova_mb_1.model.User
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class UserRepositoryImplIntegrationTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var apiService: ApiService
    private lateinit var userRepository: UserRepository
    private lateinit var mockWebServer: MockWebServer
    private lateinit var context: Context

    @Before
    fun setup() {
        mockWebServer = MockWebServer()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        userRepository = UserRepositoryImpl(apiService)
        context = ApplicationProvider.getApplicationContext()

        hiltRule.inject()
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getUsers_shouldReturnListOfUsers_whenSuccessful() = runBlocking {

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

        val result = userRepository.getUsers()

        assertThat(result).isEqualTo(
            listOf(
                User(1, "User 1", "user1", "user1@example.com", "123456789", "www.user1.com"),
                User(2, "User 2", "user2", "user2@example.com", "987654321", "www.user2.com")
            )
        )
    }

    @Test
    fun getUsers_shouldReturnEmptyList_whenResponseBodyIsNull() = runBlocking {

        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("")
        mockWebServer.enqueue(mockResponse)

        val result = userRepository.getUsers()

        assertThat(result).isEqualTo(emptyList<User>())
    }

    @Test
    fun getUsers_shouldReturnEmptyList_whenResponseIsNotSuccessful() = runBlocking {

        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
        mockWebServer.enqueue(mockResponse)

        val result = userRepository.getUsers()

        assertThat(result).isEqualTo(emptyList<User>())
    }
}