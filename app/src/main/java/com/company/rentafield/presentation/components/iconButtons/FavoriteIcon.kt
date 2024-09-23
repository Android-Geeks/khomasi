package com.company.rentafield.presentation.components.iconButtons

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.company.rentafield.R
import com.company.rentafield.presentation.theme.RentafieldTheme

@Composable
fun FavoriteIcon(
    onFavoriteClick: () -> Unit,
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
) {
    Icon(
        painter = painterResource(id = if (isFavorite) R.drawable.heart_fill else R.drawable.heart),
        contentDescription = null,
        tint = if (isFavorite) MaterialTheme.colorScheme.primary else Color.White,
        modifier = modifier.clickable {
            onFavoriteClick()
        }
    )
}

@Preview
@Composable
fun FavoriteIconPreview() {
    RentafieldTheme {
        FavoriteIcon(
            onFavoriteClick = { },
            isFavorite = true
        )
    }
}
