package com.company.rentafield.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.company.rentafield.R
import com.company.rentafield.presentation.theme.RentafieldTheme

@ExperimentalMaterial3Api
@Composable
fun DoneSuccessfully() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.done_successfully))

    BasicAlertDialog(
        onDismissRequest = {},
        modifier = Modifier
            .shadow(shape = MaterialTheme.shapes.medium, elevation = 1.dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                composition = composition,
                speed = 1.5f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
            )
            Text(
                text = "Booking Done Successfully!",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}


@ExperimentalMaterial3Api
@Preview
@Composable
private fun DoneSuccessfullyPrev() {
    RentafieldTheme {
        DoneSuccessfully()
    }
}