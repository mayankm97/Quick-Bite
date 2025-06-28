package com.example.quickbite.ui.feature.menu.image

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import coil3.compose.AsyncImage


@Composable
fun ImagePickerScreen(navController: NavController) {
    val context = LocalContext.current
    val selectedImageUri = remember {
        mutableStateOf<Uri?>(null)
    }
    val coroutineScope = rememberCoroutineScope()

    val imagePickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                selectedImageUri.value = uri
            } else {
                Toast.makeText(context, "Image not selected", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
        }

    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                imagePickerLauncher.launch("image/*")
            }
        }

    LaunchedEffect (key1 = true) {
        if (context.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            imagePickerLauncher.launch("image/*")
        } else {
            permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            model = selectedImageUri.value,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = {
            navController.previousBackStackEntry?.savedStateHandle?.set(
                "imageUri",
                selectedImageUri.value
            )
            navController.popBackStack()

        }) {
            Text(text = "Select Image")
        }

    }

}