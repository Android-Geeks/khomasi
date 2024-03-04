package com.company.khomasi.presentation.components.connectionStates

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.commandiron.compose_loading.ThreeBounce
import com.company.khomasi.theme.KhomasiTheme

@Composable
fun Loading(modifier : Modifier = Modifier){
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0x80000000)),
        contentAlignment = Alignment.Center
    ){
        ThreeBounce(
            color = MaterialTheme.colorScheme.primary,
            size = DpSize(45.dp, 45.dp),
        )
    }
}

@Preview
@Composable
fun LoadingPreview() {
    KhomasiTheme{ Loading() }
}