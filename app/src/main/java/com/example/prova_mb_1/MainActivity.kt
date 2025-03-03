package com.example.prova_mb_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.ViewCompat
import com.example.prova_mb_1.User
import com.example.prova_mb_1.ui.UserDetailScreen
import com.example.prova_mb_1.ui.UserListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserAppWithInsets()
        }
    }
}

@Composable
fun UserAppWithInsets() {
    val context = LocalContext.current
    val insets = remember { mutableStateOf(WindowInsetsCompat.CONSUMED) }
    var selectedUser by remember { mutableStateOf<User?>(null) }

    DisposableEffect(Unit) {
        val window = (context as? ComponentActivity)?.window
        window?.let {
            ViewCompat.setOnApplyWindowInsetsListener(it.decorView) { _, newInsets ->
                insets.value = newInsets
                newInsets
            }
        }
        onDispose {}
    }

    @OptIn(ExperimentalMaterial3Api::class) // allow use of experimental API for TopAppBar
    Scaffold(topBar = {
        TopAppBar(title = { Text("Users List") })
    }) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            if (selectedUser == null) {
                UserListScreen(onUserClick = { selectedUser = it })
            } else {
                UserDetailScreen(user = selectedUser, onBack = { selectedUser = null })
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (Debug.debug) {
                Debug.showMessage(context, "Debug mode enabled")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserAppWithInsets() {
    UserAppWithInsets()
}
