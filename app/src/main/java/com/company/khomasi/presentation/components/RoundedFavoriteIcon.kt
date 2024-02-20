package com.company.khomasi.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.theme.KhomasiTheme

@Composable
fun RoundedFavoriteIcon(modifier: Modifier = Modifier) {
    var isFavorite by remember { mutableStateOf(true) }

    Box(
        modifier = modifier
            .width(44.dp)
            .height(44.dp)
            .background(
                color = if (isFavorite) MaterialTheme.colorScheme.error else Color.White,
               // color= Color(color=MaterialTheme.colorScheme.primary),
                shape = CircleShape
            )
            .clickable {
                isFavorite = !isFavorite
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.heart),
            contentDescription = " ",
            modifier = modifier
                .padding(10.dp)
                .fillMaxSize(),
            tint = if (isFavorite) Color.White else Color.Black
        )
    }
}

@Preview
@Composable
fun RoundedFavoriteIconPreview() {
    KhomasiTheme {
    RoundedFavoriteIcon()
}
}
