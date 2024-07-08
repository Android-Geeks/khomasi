package com.company.rentafield.presentation.ai

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.company.rentafield.BuildConfig
import com.company.rentafield.R
import com.company.rentafield.presentation.UploadVideoWorker
import com.company.rentafield.presentation.components.MyButton
import com.company.rentafield.utils.createVideoFile
import java.util.Objects

@OptIn(ExperimentalMaterial3Api::class)
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
                permissionLauncher.launch(android.Manifest.permission.CAMERA)
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
            ExoPlayerView(
                videoUri = videoUri,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MyButton(
                    text = R.string.cancel,
                    color = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    onClick = {
                        currentVideoUri = Uri.EMPTY
                        selectedVideoUri = null
                    },
                    modifier = Modifier.weight(1f)
                )
                MyButton(
                    text = R.string.upload,
                    onClick = {
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
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        } else {
            AiScreenContent(onBackClicked = onBackClicked) { isSheetVisible = true }
        }
    }
}
