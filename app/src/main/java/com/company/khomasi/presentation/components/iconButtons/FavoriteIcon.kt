package com.company.khomasi.presentation.components.iconButtons

import androidx.compose.foundation.clickable
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
fun FavoriteIcon(
    onFavoriteClick: () -> Unit,
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    inactiveColor: Color = Color.White,
) {
    Icon(
        painter = painterResource(id = if (isFavorite) R.drawable.heart_fill else R.drawable.heart),
        contentDescription = null,
        tint = if (isFavorite) MaterialTheme.colorScheme.primary else inactiveColor,
        modifier = modifier.clickable {
            onFavoriteClick()
        }
    )
}

@Preview
@Composable
fun FavoriteIconPreview() {
    KhomasiTheme {
        FavoriteIcon(
            onFavoriteClick = { },
            isFavorite = false
        )
    }
}
