package com.example.prova_mb_1.ui

import com.example.prova_mb_1.model.User
import org.junit.Assert.assertEquals
import org.junit.Test

class UserDetailScreenTest {

    @Test
    fun `getUserField with null user should return empty string`() {
        // Arrange
        val user: User? = null

        // Act
        val result = getUserField(user, 1)

        // Assert
        assertEquals("", result)
    }

    @Test
    fun `getUserField with menuItem 1 should return username`() {
        // Arrange
        val user = User(1, "John Doe", "johndoe", "john@example.com")

        // Act
        val result = getUserField(user, 1)

        // Assert
        assertEquals("johndoe", result)
    }

    @Test
    fun `getUserField with menuItem 1 should return empty string when username is null`() {
        // Arrange
        val user = User(1, "John Doe", email = "john@example.com")

        // Act
        val result = getUserField(user, 1)

        // Assert
        assertEquals("", result)
    }

    @Test
    fun `getUserField with menuItem 2 should return phone`() {
        // Arrange
        val user = User(1, "John Doe", phone = "123456789")

        // Act
        val result = getUserField(user, 2)

        // Assert
        assertEquals("123456789", result)
    }

    @Test
    fun `getUserField with menuItem 2 should return empty string when phone is null`() {
// Arrange