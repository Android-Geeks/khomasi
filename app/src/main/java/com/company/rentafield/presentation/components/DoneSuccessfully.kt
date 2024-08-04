package com.company.rentafield.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.company.rentafield.R
import com.company.rentafield.theme.RentafieldTheme

@Composable
fun DoneSuccessfully() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.done_successfully))
    val progress by animateLottieCompositionAsState(composition)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray.copy(alpha = 0.3f)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card(
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(200.dp)
        ) {
            LottieAnimation(
                composition = composition,
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun DoneSuccessfullyPrev() {
    RentafieldTheme {
        DoneSuccessfully()
    }
}