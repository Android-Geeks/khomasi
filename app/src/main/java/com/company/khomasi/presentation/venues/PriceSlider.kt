package com.company.khomasi.presentation.venues

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.theme.darkText
import com.company.khomasi.theme.lightText
import com.smarttoolfactory.slider.LabelPosition
import com.smarttoolfactory.slider.MaterialSliderDefaults
import com.smarttoolfactory.slider.SliderBrushColor
import com.smarttoolfactory.slider.SliderWithLabel
import kotlin.math.roundToInt


@SuppressLint("UnrememberedMutableState")
@Composable
fun PriceSlider(
    isDark: Boolean = isSystemInDarkTheme(),
    initValue: Float = 50f,
    maxValue: Float = 100f
) {
    val labelProgress = remember {
        mutableFloatStateOf(initValue)
    }
    Column() {
        SliderWithLabel(
            value = labelProgress.floatValue,
            onValueChange = { labelProgress.floatValue = it },
            thumbRadius = 5.dp,
            trackHeight = 10.dp,
            valueRange = 30f..maxValue,
            colors = MaterialSliderDefaults.materialColors(
                activeTrackColor = SliderBrushColor(MaterialTheme.colorScheme.primary),
                inactiveTrackColor = SliderBrushColor(if (isDark) darkText else lightText)
            ),
            labelPosition = LabelPosition.Top,
            yOffset = (18).dp,
            label = {
                SliderItem(thumbContent = labelProgress.floatValue.roundToInt())
            },
            coerceThumbInTrack = true
        )
    }
}

@Composable
fun SliderItem(thumbContent: Int) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy((-7).dp)
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.rectangle),
                modifier = Modifier
                    .width(59.dp)
                    .height(29.dp),
                contentDescription = null,
            )
            Text(
                text = "$thumbContent ${stringResource(id = R.string.price_egp)}",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(59.dp),
                maxLines = 1
            )

        }
        Image(
            painter = painterResource(id = R.drawable.triangle),
            modifier = Modifier
                .width(24.dp)
                .height(16.dp),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.height(6.dp))

        Image(
            painter = painterResource(id = R.drawable.navigationarrow),
            modifier = Modifier
                .padding(end = 1.dp)
                .size(20.dp),
            contentDescription = null
        )

    }
}

@Preview
@Composable
fun PriceSliderPreview() {
    KhomasiTheme {
        PriceSlider()
    }
}