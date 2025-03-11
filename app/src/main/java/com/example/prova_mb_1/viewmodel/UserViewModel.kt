package com.example.prova_mb_1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prova_mb_1.model.User
import com.example.prova_mb_1.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UserListState(
    val isLoading: Boolean = false,
    val users: List<User> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userListState = MutableStateFlow(UserListState())
    val userListState: StateFlow<UserListState> = _userListState.asStateFlow()

    init {
        loadUsers()
    }

    fun loadUsers() {
        _userListState.value = _userListState.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val users = userRepository.getUsers()
                _userListState.value = _userListState.value.copy(
                    isLoading = false,
                    users = users,
                    error = null
                )
            } catch (e: Exception) {
                _userListState.value = _userListState.value.copy(
                    isLoading = false,
                    error = "Error loading users: ${e.message}"
                )
            }
        }
    }
}