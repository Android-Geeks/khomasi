package com.company.khomasi.presentation.venues.component

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.khomasi.R
import com.company.khomasi.presentation.venues.BrowseUiState
import com.smarttoolfactory.slider.LabelPosition
import com.smarttoolfactory.slider.MaterialSliderDefaults
import com.smarttoolfactory.slider.SliderBrushColor
import com.smarttoolfactory.slider.SliderWithLabel
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.roundToInt

@Composable
fun PriceSlider(
    filteredUiState: StateFlow<BrowseUiState>,
    setPrice: (Int) -> Unit
) {
    val uiState by filteredUiState.collectAsStateWithLifecycle()
    val labelProgress = remember {
        mutableFloatStateOf(uiState.price.toFloat())
    }
    LaunchedEffect(labelProgress.floatValue) {
        setPrice(labelProgress.floatValue.roundToInt())
    }
    SliderWithLabel(
        value = labelProgress.floatValue,
        onValueChange = { labelProgress.floatValue = it },
        thumbRadius = 0.dp,
        trackHeight = 10.dp,
        valueRange = 30f..uiState.maxValue,
        colors = MaterialSliderDefaults.materialColors(
            activeTrackColor = SliderBrushColor(MaterialTheme.colorScheme.primary),
            inactiveTrackColor = SliderBrushColor(MaterialTheme.colorScheme.onPrimaryContainer)
        ),
        labelPosition = LabelPosition.Top,
        yOffset = (18).dp,
        label = {
            SliderItem(thumbContent = labelProgress.floatValue.roundToInt())
        }
    )
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