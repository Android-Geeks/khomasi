package com.company.khomasi.presentation.ui.screens


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.company.khomasi.theme.KhomasiTheme

@Composable
fun FavoriteScreen(){

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "Favorite Screen"
        )
    }
}



@Preview(showSystemUi = true)
@Composable
fun FavoritePreview(){

    KhomasiTheme {
        FavoriteScreen()
    }
}