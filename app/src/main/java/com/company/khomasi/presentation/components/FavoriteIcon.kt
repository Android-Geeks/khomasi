package com.company.khomasi.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasi.R

@Composable
fun FavoriteIcon() {
    var isFavorite by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .clickable {
                isFavorite = !isFavorite
            }
    ) {
        if (isFavorite) {
            Icon(
                painter = painterResource(id = R.drawable.heart_fill),
                contentDescription = " ",
                tint = Color.Green
            )
        } else {
            Icon(
                painter = painterResource(id = R.drawable.heart),
                contentDescription = " ",
            )
        }
    }
}

@Preview
@Composable
fun PreviewFavoriteIcon() {
    FavoriteIcon()
}
