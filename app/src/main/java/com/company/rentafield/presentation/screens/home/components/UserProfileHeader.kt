package com.company.rentafield.presentation.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.company.rentafield.R
import com.company.rentafield.utils.ThemePreviews
import com.company.rentafield.utils.convertToBitmap

@Composable
fun UserProfileHeader(
    userFirstName: String,
    profileImage: String?,
    onClickUserImage: () -> Unit,
    onClickBell: () -> Unit,
    modifier: Modifier=Modifier
) {
    val imageRequest = remember(profileImage) {
        profileImage?.convertToBitmap() ?: ""
    }
    Row(modifier = modifier) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface)
                .clickable { onClickUserImage() },
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(imageRequest)
                .crossfade(true).build(),
            loading = {
                CircularProgressIndicator()
            },
            error = {
                Image(
                    painter = painterResource(id = R.drawable.user_img),
                    contentDescription = null
                )
            },
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = "${stringResource(id = R.string.hello)} $userFirstName",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Text(
                text = stringResource(id = R.string.welcome_message),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer

            )
        }
        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = onClickBell) {
            Icon(
                painter = painterResource(id = R.drawable.bell),
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                contentDescription = null
            )
        }
    }
}

@ThemePreviews
@Composable
fun ProfileHeaderPreview() {
    UserProfileHeader(
        userFirstName = "Android",
        profileImage = null,
        onClickUserImage = {},
        onClickBell = {})
}