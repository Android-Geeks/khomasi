package com.company.rentafield.presentation.screens.onboarding.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.rentafield.theme.RentafieldTheme

@Composable
fun PagerIndicator(
    modifier: Modifier = Modifier,
    pagesSize: Int,
    selectedPage: Int,
    selectedColor: Color = MaterialTheme.colorScheme.secondary,
    unselectedColor: Color = MaterialTheme.colorScheme.tertiary,
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        repeat(times = pagesSize) { page ->
            Box(
                modifier = Modifier
                    .height(8.dp)
                    .size(if (page == selectedPage) 34.dp else 16.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(color = if (page == selectedPage) selectedColor else unselectedColor),
            )
            if (page != pagesSize - 1) Spacer(modifier = Modifier.width(4.dp))
        }
    }
}

@Preview(name = "Light", uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "Dark", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PagerIndicatorPreview() {
    RentafieldTheme {
        PagerIndicator(
            pagesSize = 2,
            selectedPage = 1
        )
    }
}