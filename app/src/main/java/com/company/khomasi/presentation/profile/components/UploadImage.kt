package com.company.khomasi.presentation.profile.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.company.khomasi.R
import com.company.khomasi.presentation.components.MyTextButton
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.utils.convertToBitmap
import com.company.khomasi.utils.toBase64String

@Composable
fun PhotoSelectorView(
    oldPic: String?,
    onChangeProfileImage: (String) -> Unit
) {
    var selectedImage by remember {
        mutableStateOf<Uri?>(null)
    }


    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImage = uri }
    )


    fun launchPhotoPicker() {
        singlePhotoPickerLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }

    SubcomposeAsyncImage(
        model = ImageRequest
            .Builder(context = LocalContext.current)
            .data(selectedImage ?: oldPic?.convertToBitmap())
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

    if (selectedImage != null) {
        onChangeProfileImage(selectedImage!!.toBase64String(LocalContext.current))
    }
    MyTextButton(
        text = R.string.change,
        onClick = { launchPhotoPicker() },
        isUnderlined = false
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KhomasiTheme {
        PhotoSelectorView("", onChangeProfileImage = { })
    }
}