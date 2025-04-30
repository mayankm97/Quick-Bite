package com.example.quickbite.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.quickbite.R

@Composable
fun NotificationScreen() {
    // Simulate empty notification list
    val hasNotifications = false

    if (!hasNotifications) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Image (should be in res/drawable or use painterResource)
            Image(
                painter = painterResource(id = R.drawable.ic_notification), // your image in drawable
                contentDescription = "No Notifications",
                modifier = Modifier
                    .size(30.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Text message
            Text(
                text = "No notifications",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    } else {
        // Show actual notifications here
    }
}
