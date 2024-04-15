package com.company.khomasi.presentation.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.company.khomasi.R
import com.company.khomasi.theme.darkText
import com.company.khomasi.theme.lightText
import com.company.khomasi.utils.convertToBitmap

@Composable
fun ProfileImage(
    name: String,
    image: String?,
    rating: Double,
    isDark: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        AsyncImage(
            model = ImageRequest
                .Builder(context = LocalContext.current)
                .data(image?.convertToBitmap())
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.playground),
            error = painterResource(id = R.drawable.user_img),
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface)
        )
        Text(
            text = name,
            style = MaterialTheme.typography.displayMedium,
            color = if (isDark) darkText else lightText
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.unfilled_star),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.tertiary
            )
            Text(
                text = String.format("%.1f", rating),
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.tertiary
            )
        }

    }
}