package com.company.khomasi.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.company.khomasi.R
import com.company.khomasi.theme.KhomasiTheme

@Composable
fun FavoriteIcon(modifier : Modifier = Modifier
) {
    var isFavorite by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .clickable {
                isFavorite = !isFavorite
            }
    ) {
            Icon(
                painter = painterResource(id = if (isFavorite) R.drawable.heart_fill else R.drawable.heart),
                contentDescription = " ",
                tint = if (isFavorite) MaterialTheme.colorScheme.primary else Color.White
            )
    }
}

@Preview
@Composable
fun FavoriteIconPreview() {
    KhomasiTheme {
    FavoriteIcon()
    }
}
