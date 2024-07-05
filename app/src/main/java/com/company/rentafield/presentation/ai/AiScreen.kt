package com.company.rentafield.presentation.ai

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.company.rentafield.BuildConfig
import com.company.rentafield.R
import com.company.rentafield.presentation.UploadVideoWorker
import com.company.rentafield.utils.createVideoFile
import java.util.Objects

@Composable
fun AiScreen(
    userId: String,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var currentVideoUri by remember {
        mutableStateOf(value = Uri.EMPTY)
    }
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    Log.d("AiScreen", "currentVideoUri: $currentVideoUri")

    val videoFile by remember { mutableStateOf(context.createVideoFile()) }
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        BuildConfig.APPLICATION_ID + ".provider", videoFile
    )

    val singleVideoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uriImage ->
            selectedImageUri = uriImage
            if (selectedImageUri != null) {
                val workManager: WorkManager = WorkManager.getInstance(context)
                val inputData =
                    workDataOf("id" to userId, "videoFilePath" to selectedImageUri.toString())
                val uploadWorkRequest = OneTimeWorkRequestBuilder<UploadVideoWorker>()
                    .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                    .setInputData(inputData)
                    .build()
                workManager.enqueue(uploadWorkRequest)
            }
        }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CaptureVideo(),
        onResult = { isSuccess ->
            currentVideoUri = uri
            if (isSuccess) {
                val workManager: WorkManager = WorkManager.getInstance(context)
                val inputData = workDataOf("id" to userId, "videoFilePath" to currentVideoUri)
                val uploadWorkRequest = OneTimeWorkRequestBuilder<UploadVideoWorker>()
                    .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                    .setInputData(inputData)
                    .build()
                workManager.enqueue(uploadWorkRequest)
            }
        }
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, R.string.permission_denied, Toast.LENGTH_SHORT).show()
        }
    }


    Button(onClick = {
        singleVideoPicker.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly)
        )
    }) {
        Text("Upload Video")
    }
}