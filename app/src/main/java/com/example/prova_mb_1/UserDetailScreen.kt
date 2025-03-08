package com.example.prova_mb_1.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.prova_mb_1.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(user: User?, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("User details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Go back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            user?.let {
                Text(text = it.name, style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "E-mail: ${it.email}", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(16.dp))
                if (!it.phone.isNullOrEmpty()){
                    Text(text = "Phone: ${it.phone}", style = MaterialTheme.typography.bodyMedium)
                }

                var info: String //showing more info of the user with data extracted from json
                if (it.username.isNullOrEmpty()){
                    //do not show the field info if phone and website are null or empty
                    if (it.website.isNullOrEmpty()){
                        //do not show the field info if phone and website are null or empty
                        info = ""
                    } else {
                        info = "Description: Website is " + getUserField(it, 3) //only show website in field info
                    }
                } else {
                    if (it.website.isNullOrEmpty()){
                        info = "Username: "+ getUserField(it, 1) //only show username in field info
                    } else {
                        info = "Username is "+ getUserField(it, 1) + " and website is " + getUserField(it, 3) //show both username and website
                    }
                }
                if (!info.isNullOrEmpty()) {
                    Text(text = info, style = MaterialTheme.typography.bodyMedium)
                }
            } ?: Text("User not found")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserDetailScreen() {
    UserDetailScreen(user = User(1, "John Doe", "jd", "john@example.com","+1 234 567 890", "web.site.com"), onBack = {})
}


/**
 * Retrieves a specific field from a User object.
 *
 * @author [Alex Saborit, assaborit@gmail.com]
 *
 * @param user User object containing the requested field. If null, an empty string is returned.
 * @param menuItem Integer indicating the field to retrieve:
 *     - 1: Username
 *     - 2: Phone
 *     - 3: Website
 * @return String containing the selected field or an empty string if the user is null.
*/
fun getUserField(user: User?, menuItem: Int): String {
    return when (menuItem) {
        1 -> user?.username ?: ""
        2 -> user?.phone ?: ""
        3 -> user?.website ?: ""
        else -> ""
    }
}
