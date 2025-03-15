package com.example.prova_mb_1

import com.example.prova_mb_1.model.User
import com.example.prova_mb_1.ui.getUserField
import org.junit.Assert.assertEquals
import org.junit.Test

class UserDetailScreenTest {
    @Test
    fun getUserField_shouldReturnEmptyString_whenUserIsNull() {
        // Arrange
        val user: User? = null

        // Act
        val result = getUserField(user, 1)

        // Assert
        assertEquals("", result)
    }

    @Test
    fun getUserField_shouldReturnUsername_whenMenuItemIs1() {
        // Arrange
        val user = User(1, "John Doe", "johndoe", "john@example.com")

        // Act
        val result = getUserField(user, 1)

        // Assert
        assertEquals("johndoe", result)
    }

    @Test
    fun getUserField_shouldReturnEmptyString_whenMenuItemIs1AndUsernameIsNull() {
        // Arrange
        val user = User(1, "John Doe", email = "john@example.com")

        // Act
        val result = getUserField(user, 1)

        // Assert
        assertEquals("", result)
    }
    @Test
    fun getUserField_shouldReturnPhone_whenMenuItemIs2() {
        // Arrange
        val user = User(1, "John Doe", phone = "123456789")

        // Act
        val result = getUserField(user, 2)

        // Assert
        assertEquals("123456789", result)
    }

    @Test
    fun getUserField_shouldReturnEmptyString_whenMenuItemIs2AndPhoneIsNull() {
        // Arrange
        val user = User(1, "John Doe")

        // Act
        val result = getUserField(user, 2)

        // Assert
        assertEquals("", result)
    }

    @Test
    fun getUserField_shouldReturnWebsite_whenMenuItemIs3() {
        // Arrange
        val user = User(1, "John Doe", website = "www.johndoe.com")

        // Act
        val result = getUserField(user, 3)

        // Assert
        assertEquals("www.johndoe.com", result)
    }

    @Test
    fun getUserField_shouldReturnEmptyString_whenMenuItemIs3AndWebsiteIsNull() {
        // Arrange
        val user = User(1, "John Doe")

        // Act
        val result = getUserField(user, 3)

        // Assert
        assertEquals("", result)
    }

    @Test
    fun getUserField_shouldReturnEmptyString_whenMenuItemIsInvalid() {
        // Arrange
        val user = User(1, "John Doe")

        // Act
        val result = getUserField(user, 99)

        // Assert
        assertEquals("", result)
    }
}