package com.company.khomasi.presentation.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

import com.company.khomasi.theme.KhomasiTheme

@Composable
fun MyApp() {
    KhomasiTheme(
        darkTheme = true,
    ) {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KhomasiTheme {
        MyApp()
    }
}