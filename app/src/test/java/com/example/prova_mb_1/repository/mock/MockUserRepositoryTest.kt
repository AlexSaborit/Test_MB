package com.example.prova_mb_1.repository.mock

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class MockUserRepositoryTest {

    private val mockUserRepository = MockUserRepository()

    @Test
    fun `getUsers should return a non-null list`() = runTest {
        val users = mockUserRepository.getUsers()
        assertNotNull(users)
    }

    @Test
    fun `getUsers should return a list with 2 elements`() = runTest {
        val users = mockUserRepository.getUsers()
        assertEquals(2, users.size)
    }

    @Test
    fun `getUsers should return the correct first user`() = runTest {
        val users = mockUserRepository.getUsers()
        val firstUser = users[0]
        assertEquals(1, firstUser.id)
        assertEquals("Mock User 1", firstUser.name)
        assertEquals("mock1@example.com", firstUser.email)
    }

    @Test
    fun `getUsers should return the correct second user`() = runTest {
        val users = mockUserRepository.getUsers()
        val secondUser = users[1]
        assertEquals(2, secondUser.id)
        assertEquals("Mock User 2", secondUser.name)
        assertEquals("mock2@example.com", secondUser.email)
    }
}