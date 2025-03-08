package com.example.prova_mb_1.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.prova_mb_1.model.User
import com.example.prova_mb_1.api.UserService
import com.example.prova_mb_1.api.RetrofitClient
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(onUserClick: (User) -> Unit) {
    val userService: UserService = RetrofitClient.userService
    var users by remember { mutableStateOf<List<User>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                users = userService.getUsers()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    Scaffold(topBar = { TopAppBar(title = { Text("User List") }) }) { paddingValues ->
        LazyColumn(modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp)) {
            items(users) { user ->
                UserCard(user = user, onUserClick = onUserClick)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCard(user: User, onUserClick: (User) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = { onUserClick(user) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = user.name, style = MaterialTheme.typography.titleMedium)
            Text(text = user.email, style = MaterialTheme.typography.bodyMedium)

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserListScreen() {
    UserListScreen(onUserClick = {})
}
