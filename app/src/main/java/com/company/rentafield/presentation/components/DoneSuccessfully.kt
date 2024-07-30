package com.company.rentafield.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.company.rentafield.R

@Composable
fun DoneSuccessfully() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.done_successfully))
    val progress by animateLottieCompositionAsState(composition)

    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = Modifier.size(200.dp)
    )
}