package com.example.prova_mb_1.viewmodel

import android.content.Context
import androidx.compose.ui.semantics.error
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.prova_mb_1.MainDispatcherRule
import com.example.prova_mb_1.api.ApiService
import com.example.prova_mb_1.model.User
import com.example.prova_mb_1.repository.UserRepository
import com.example.prova_mb_1.repository.UserRepositoryImpl
import com.google.common.truth.Truth8.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
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
@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModelIntegrationTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var mainDispatcherRule = MainDispatcherRule()

    private lateinit var apiService: ApiService
    private lateinit var userRepository: UserRepository
    private lateinit var userViewModel: UserViewModel
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
        userViewModel = UserViewModel(userRepository)
        context = ApplicationProvider.getApplicationContext()
        hiltRule.inject()
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getUsers_shouldUpdateStateWithUsers_whenSuccessful() = runTest {
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
        userViewModel.getUsers()

        // Assert
        val expectedUsers = listOf(
            User(1, "User 1", "user1", "user1@example.com", "123456789", "www.user1.com"),
            User(2, "User 2", "user2", "user2@example.com", "987654321", "www.user2.com")
        )
        val actualState = userViewModel.userListState.first()
        assertThat(actualState.isLoading).isFalse()
        assertThat(actualState.users).isEqualTo(expectedUsers)
    }

    @Test
    fun getUsers_shouldUpdateStateWithError_whenRequestFails() = runTest {
        // Arrange
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
        mockWebServer.enqueue(mockResponse)

        // Act
        userViewModel.getUsers()

        // Assert
        val actualState = userViewModel.userListState.first()
        assertThat(actualState.isLoading).isFalse()
        assertThat(actualState.error).isNotNull()
    }
}