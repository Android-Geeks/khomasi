package com.company.rentafield.presentation.components.connectionStates

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.company.rentafield.theme.RentafieldTheme
import com.company.rentafield.utils.ThemePreviews


@Composable
fun Loading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0x80000000))
            .clickable(enabled = false) { },
        contentAlignment = Alignment.Center
    ) {
        ThreeBounce(
            color = MaterialTheme.colorScheme.primary,
            size = DpSize(75.dp, 75.dp)
        )
    }
}

@Composable
fun ThreeBounce(
    modifier: Modifier = Modifier,
    durationMillis: Int = 1400,
    delayBetweenDotsMillis: Int = 160,
    size: DpSize = DpSize(40.dp, 40.dp),
    color: Color = MaterialTheme.colorScheme.primary,
    shape: Shape = CircleShape
) {
    val dotSize = size * 3 / 11

    val sizeMultiplier1 = rememberBounceTransition(durationMillis)
    val sizeMultiplier2 = rememberBounceTransition(durationMillis, delayBetweenDotsMillis)
    val sizeMultiplier3 = rememberBounceTransition(durationMillis, delayBetweenDotsMillis * 2)

    Row(
        modifier = modifier.size(size),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BouncingDot(dotSize, sizeMultiplier1, color, shape)
        Spacer(modifier = Modifier.width(4.dp))
        BouncingDot(dotSize, sizeMultiplier2, color, shape)
        Spacer(modifier = Modifier.width(4.dp))
        BouncingDot(dotSize, sizeMultiplier3, color, shape)
    }
}

@Composable
fun BouncingDot(
    size: DpSize,
    sizeMultiplier: State<Float>,
    color: Color,
    shape: Shape
) {
    Box(modifier = Modifier.size(size), contentAlignment = Alignment.Center) {
        Surface(
            modifier = Modifier.size(size * sizeMultiplier.value),
            shape = shape,
            color = color
        ) {}
    }
}

@Composable
fun rememberBounceTransition(
    durationMillis: Int,
    offsetMillis: Int = 0
): State<Float> = rememberInfiniteTransition(label = "").fractionTransition(
    initialValue = 0f,
    targetValue = 1f,
    fraction = 1,
    durationMillis = durationMillis / 2,
    offsetMillis = offsetMillis,
    repeatMode = RepeatMode.Reverse
)


@ThemePreviews
@Composable
fun LoadingPreview() {
    RentafieldTheme { Loading() }
}