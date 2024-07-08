package com.company.rentafield.presentation.profile.components

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.company.rentafield.BuildConfig
import com.company.rentafield.R
import com.company.rentafield.presentation.components.MyTextButton
import com.company.rentafield.presentation.profile.components.sheets.UploadPhotoOptionsSheet
import com.company.rentafield.theme.KhomasiTheme
import com.company.rentafield.utils.convertToBitmap
import com.company.rentafield.utils.createImageFile
import com.company.rentafield.utils.createImageFromUri
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
    val file by remember { mutableStateOf(context.createImageFile()) }
    val uri by remember {
        mutableStateOf(
            FileProvider.getUriForFile(
                Objects.requireNonNull(context),
                BuildConfig.APPLICATION_ID + ".provider", file
            )
        )
    }
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
            onResult = { isPictureTaken ->
                if (isPictureTaken) {
                    capturedImageUri = uri
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

    var showChooserSheet by rememberSaveable { mutableStateOf(false) }
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
            }
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
            detectFace(context, selectedImageUri!!, { hasFace ->
                if (hasFace) {
                    val tempFile = selectedImageUri!!.createImageFromUri(context)
                    onChangeProfileImage(tempFile)
                } else {
                    Toast.makeText(context, R.string.face_not_detected, Toast.LENGTH_SHORT).show()
                    selectedImageUri = null
                }
            }, {
                Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
                selectedImageUri = null
            })
        }

        if (capturedImageUri != Uri.EMPTY) {
            detectFace(context, capturedImageUri, { hasFace ->
                if (hasFace) {
                    val tempFile = capturedImageUri.createImageFromUri(context)
                    onChangeProfileImage(tempFile)
                } else {
                    Toast.makeText(context, R.string.face_not_detected, Toast.LENGTH_SHORT).show()
                    capturedImageUri = Uri.EMPTY
                }
            }, {
                Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
                capturedImageUri = Uri.EMPTY
            })
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