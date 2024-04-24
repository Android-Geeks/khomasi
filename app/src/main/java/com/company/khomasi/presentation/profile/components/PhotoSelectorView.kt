package com.company.khomasi.presentation.profile.components

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.company.khomasi.BuildConfig
import com.company.khomasi.R
import com.company.khomasi.presentation.components.MyTextButton
import com.company.khomasi.presentation.profile.components.sheets.UploadPhotoOptionsSheet
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.utils.convertToBitmap
import com.company.khomasi.utils.createFileFromUri
import com.company.khomasi.utils.createImageFile
import kotlinx.coroutines.launch
import java.io.File
import java.util.Objects

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoSelectorView(
    profileImage: String?,
    onChangeProfileImage: (File) -> Unit,
) {
    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        BuildConfig.APPLICATION_ID + ".provider", file
    )
    val scope = rememberCoroutineScope()


    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var capturedImageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }


    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uriImage -> selectedImageUri = uriImage }
    )


    val cameraLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture(),
            onResult = { capturedImageUri = uri }
        )
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, R.string.grant_permission, Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, R.string.permission_denied, Toast.LENGTH_SHORT).show()
        }
    }

    var showChooserSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    if (showChooserSheet) {
        UploadPhotoOptionsSheet(
            bottomSheetState = sheetState,
            onDismissRequest = {
                scope.launch {
                    sheetState.hide()
                    showChooserSheet = false
                }
            },
            onTakePhoto = {
                permissionLauncher.launch(android.Manifest.permission.CAMERA)
                showChooserSheet = false
                selectedImageUri = null
            },
            onChooseFromGallery = {
                singlePhotoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
                showChooserSheet = false
                capturedImageUri = Uri.EMPTY
            },
            isDark = isSystemInDarkTheme()
        )
    }


    SubcomposeAsyncImage(
        model = ImageRequest
            .Builder(context = context)
            .data(
                if (capturedImageUri != Uri.EMPTY) capturedImageUri
                else if (selectedImageUri != null) selectedImageUri
                else profileImage?.convertToBitmap()
            )
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        loading = {
            CircularProgressIndicator()
        },
        error = {
            Image(
                painter = painterResource(id = R.drawable.user_img),
                contentDescription = null
            )
        },
        modifier = Modifier
            .size(150.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surface)
    )


    MyTextButton(
        text = R.string.change,
        onClick = {
            scope.launch {
                showChooserSheet = true
                sheetState.show()
            }
        },
        isUnderlined = false
    )

    LaunchedEffect(selectedImageUri, capturedImageUri) {
        if (selectedImageUri != null) {
            val tempFile = selectedImageUri!!.createFileFromUri(context)
            onChangeProfileImage(tempFile)
        }
        if (capturedImageUri != Uri.EMPTY) {
            val tempFile = capturedImageUri.createFileFromUri(context)
            onChangeProfileImage(tempFile)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KhomasiTheme {
        PhotoSelectorView("", onChangeProfileImage = { })
    }
}