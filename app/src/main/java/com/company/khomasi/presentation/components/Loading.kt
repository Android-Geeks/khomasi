package com.company.khomasi.presentation.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.commandiron.compose_loading.ThreeBounce
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.theme.darkHint
import com.company.khomasi.theme.lightHint

@Composable
fun Loading(modifier : Modifier = Modifier){
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        ThreeBounce(
            color = if (isSystemInDarkTheme()) lightHint else darkHint
        )
    }
}

@Preview()
@Composable
fun LoadingPreview() {
    KhomasiTheme{ Loading() }
}