package com.company.rentafield.presentation.screens.ai

import android.Manifest
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.company.rentafield.BuildConfig
import com.company.rentafield.R
import com.company.rentafield.presentation.screens.ai.components.UploadVideoOptionsSheet
import com.company.rentafield.utils.createVideoFile
import com.company.rentafield.workers.UploadVideoWorker
import java.util.Objects

@ExperimentalMaterial3Api
@Composable
fun AiScreen(
    userId: String,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState()
    var isSheetVisible by remember {
        mutableStateOf(value = false)
    }

    var currentVideoUri by rememberSaveable {
        mutableStateOf(value = Uri.EMPTY)
    }
    var selectedVideoUri by rememberSaveable {
        mutableStateOf<Uri?>(null)
    }

    val videoFile by rememberSaveable { mutableStateOf(context.createVideoFile()) }
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        BuildConfig.APPLICATION_ID + ".provider", videoFile
    )

    val singleVideoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uriImage ->
            if (uriImage != null) {
                selectedVideoUri = uriImage
            }
        }
    )


    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CaptureVideo(),
        onResult = { isSuccess ->
            if (isSuccess) {
                currentVideoUri = uri
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

    if (isSheetVisible) {
        UploadVideoOptionsSheet(
            bottomSheetState = sheetState,
            onDismissRequest = { isSheetVisible = false },
            onCaptureVideo = {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            },
            onChooseFromGallery = {
                singleVideoPicker.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly)
                )
            }
        )
    }

    Column(
        modifier
            .fillMaxSize()
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val videoUri = currentVideoUri.takeIf { it != Uri.EMPTY } ?: selectedVideoUri
        if (videoUri != null) {
            AiVideoUpload(
                modifier = Modifier.weight(1f),
                videoUri = videoUri,
                onUpload = {
                    val workManager: WorkManager = WorkManager.getInstance(context)
                    val inputData = workDataOf(
                        "id" to userId,
                        "videoFilePath" to videoUri.toString()
                    )
                    val uploadWorkRequest = OneTimeWorkRequestBuilder<UploadVideoWorker>()
                        .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                        .setInputData(inputData)
                        .build()
                    workManager.enqueue(uploadWorkRequest)
                    onBackClicked()
                },
                onCancel = {
                    currentVideoUri = Uri.EMPTY
                    selectedVideoUri = null
                }
            )
        } else {
            AiInstructions(onBackClicked = onBackClicked) { isSheetVisible = true }
        }
    }
}
