package com.company.khomasi.presentation.components

import android.content.Context
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.company.khomasi.utils.navigateToSettings

@Composable
fun MyAlertDialog(context: Context) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text("Location Permission Required") },
        text = { Text("This app needs location permissions to work properly. Please enable the location permission in settings.") },
        confirmButton = {
            TextButton(
                onClick = { navigateToSettings(context) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Go to Settings")
            }
        }
    )
}

