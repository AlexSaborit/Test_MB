package com.example.prova_mb_1.ui

import com.example.prova_mb_1.model.User
import org.junit.Assert.assertEquals
import org.junit.Test

class UserDetailScreenTest {

    @Test
    fun `getUserField with null user should return empty string`() {
        val result = getUserField(null, 1)
        assertEquals("", result)
    }

    @Test
    fun `getUserField with menuItem 1 should return username or empty string`() {
        val user = User(1, "John Doe", "johndoe", "john@example.com", "123456789", "www.example.com")
        val result = getUserField(user, 1)
        assertEquals("johndoe", result)

        val userWithoutUsername = User(1, "John Doe", "", "john@example.com", "123456789", "www.example.com")
        val resultWithoutUsername = getUserField(userWithoutUsername, 1)
        assertEquals("", resultWithoutUsername)
    }

    @Test
    fun `getUserField with menuItem 2 should return phone or empty string`() {
        val user = User(1, "John Doe", "johndoe", "john@example.com", "123456789", "www.example.com")
        val result = getUserField(user, 2)
        assertEquals("123456789", result)

        val userWithoutPhone = User(1, "John Doe", "johndoe", "john@example.com", "", "www.example.com")
        val resultWithoutPhone = getUserField(userWithoutPhone, 2)
        assertEquals("", resultWithoutPhone)
    }

    @Test
    fun `getUserField with menuItem 3 should return website or empty string`() {
        val user = User(1, "John Doe", "johndoe", "john@example.com", "123456789", "www.example.com")
        val result = getUserField(user, 3)
        assertEquals("www.example.com", result)

        val userWithoutWebsite = User(1, "John Doe", "johndoe", "john@example.com", "123456789", "")
        val resultWithoutWebsite = getUserField(userWithoutWebsite, 3)
        assertEquals("", resultWithoutWebsite)
    }

    @Test
    fun `getUserField with invalid menuItem should return empty string`() {
        val user = User(1, "John Doe", "johndoe", "john@example.com", "123456789", "www.example.com")
        val result = getUserField(user, 4)
        assertEquals("", result)
    }
}