package com.example.prova_mb_1.viewmodel

import com.example.prova_mb_1.model.User
import com.example.prova_mb_1.repository.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModelTest {

    private val userRepository: UserRepository = mockk()
    private lateinit var userViewModel: UserViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should call loadUsers`() = runTest {
        // Arrange
        coEvery { userRepository.getUsers() } returns emptyList()

        // Act
        userViewModel = UserViewModel(userRepository)

        // Assert
        coVerify(exactly = 1) { userRepository.getUsers() }
    }

    @Test
    fun `loadUsers should emit list of users`() = runTest {
        // Arrange
        val users = listOf(User(1, "User 1"))
        coEvery { userRepository.getUsers() } returns users

        // Act
        userViewModel = UserViewModel(userRepository)

        // Assert
        assertEquals(users, userViewModel.users.first())
    }

    @Test
    fun `loadUsers should emit empty list when getUsers returns empty list`() = runTest {
        // Arrange
        coEvery { userRepository.getUsers() } returns emptyList()

        // Act
        userViewModel = UserViewModel(userRepository)

        // Assert
        assertEquals(emptyList<User>(), userViewModel.users.first())
    }
}