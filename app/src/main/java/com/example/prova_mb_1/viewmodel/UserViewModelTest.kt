package com.example.prova_mb_1.viewmodel

import com.example.prova_mb_1.model.User
import com.example.prova_mb_1.repository.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModelTest {

    private lateinit var userRepository: UserRepository
    private lateinit var viewModel: UserViewModel
    private val testDispatcher = StandardTestDispatcher()

    //TODO("JAVADOC OR SIMILAR")
    @Before
    fun setUp() {
        userRepository = mockk()
        Dispatchers.setMain(testDispatcher)
    }

    //TODO("JAVADOC OR SIMILAR")
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    //TODO("JAVADOC OR SIMILAR")
    @Test
    fun `loadUsers - success`() = runTest {
        // Given
        val users = listOf(User(1, "John Doe", "john@example.com", ""))
        coEvery { userRepository.getUsers() } returns users

        // When
        viewModel = UserViewModel(userRepository)
        launch { viewModel.users.collect {  } } // Collecting the Flow, no error without this line

        // Then
        testDispatcher.scheduler.advanceUntilIdle() // Advance the scheduler to complete all coroutines
        assertEquals(users, viewModel.users.value)
    }

    //TODO("JAVADOC OR SIMILAR")
    @Test
    fun `loadUsers - empty`() = runTest {
        // Given
        val users = emptyList<User>()
        coEvery { userRepository.getUsers() } returns users

        // When
        viewModel = UserViewModel(userRepository)
        launch { viewModel.users.collect {  } } // Collecting the Flow, no error without this line

        // Then
        testDispatcher.scheduler.advanceUntilIdle() // Advance the scheduler to complete all coroutines
        assertEquals(users, viewModel.users.value)
    }
}