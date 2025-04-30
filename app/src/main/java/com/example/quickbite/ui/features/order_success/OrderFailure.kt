package com.example.quickbite.ui.features.order_success

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.quickbite.ui.navigation.Home

@Composable
fun OrderFailure() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        Text(text = "Order Success", style = MaterialTheme.typography.titleMedium)
        Text(
            text = "We could not process your payment.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}