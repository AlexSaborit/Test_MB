package com.example.prova_mb_1.di

import com.example.prova_mb_1.api.ApiService
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitModuleTest {

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `provideRetrofit should return Retrofit with correct base URL and GsonConverterFactory`() {
        // Arrange
        val retrofit = RetrofitModule.provideRetrofit()

        // Assert
        assertNotNull(retrofit)
        assertEquals(mockWebServer.url("/").toString(), retrofit.baseUrl().toString())
        assert(retrofit.converterFactories().any { it is GsonConverterFactory })
    }

    @Test
    fun `provideApiService should return ApiService`() {
        // Arrange
        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Act
        val apiService = RetrofitModule.provideApiService(retrofit)

        // Assert
        assertNotNull(apiService)
        assert(apiService is ApiService)
    }
}