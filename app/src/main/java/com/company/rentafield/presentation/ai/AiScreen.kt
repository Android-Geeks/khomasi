package com.company.rentafield.presentation.ai

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.company.rentafield.presentation.UploadVideoWorker

@Composable
fun AiScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val workManager: WorkManager = WorkManager.getInstance(context)
    Button(onClick = {
        val inputData = workDataOf("id" to 0, "videoFilePath" to 0)
        val uploadWorkRequest = OneTimeWorkRequestBuilder<UploadVideoWorker>()
            .setInputData(inputData)
            .build()
        workManager.enqueue(uploadWorkRequest)
    }) {
        Text("Upload Video")
    }
}