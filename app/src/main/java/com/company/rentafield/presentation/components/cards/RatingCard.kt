package com.company.rentafield.presentation.components.cards

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.company.rentafield.R
import com.company.rentafield.presentation.components.MyButton
import com.company.rentafield.theme.RentafieldTheme
import com.company.rentafield.theme.darkIcon
import com.company.rentafield.theme.darkOverlay
import com.company.rentafield.theme.darkText
import com.company.rentafield.theme.lightIcon
import com.company.rentafield.theme.lightOverlay
import com.company.rentafield.theme.lightText

@Composable
fun RatingCard(
    @StringRes buttonText: Int,
    mainText: String,
    subText: String,
    modifier: Modifier = Modifier,
    @DrawableRes timeIcon: Int = 0,
    isDark: Boolean = isSystemInDarkTheme()
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(111.dp),
        colors = CardDefaults.cardColors(if (isDark) darkOverlay else lightOverlay),
        shape = MaterialTheme.shapes.large

    ) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = mainText,
                    style = MaterialTheme.typography.displayLarge,
                    color = if (isDark) darkText else lightText,
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (timeIcon != 0) {
                        Icon(
                            painter = painterResource(timeIcon),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                    }
                    Text(
                        text = subText,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Start
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Box {
                Image(
                    painter = painterResource(id = R.drawable.lines),
                    contentDescription = null,
                    modifier = if (LocalLayoutDirection.current == LayoutDirection.Ltr) Modifier
                        .rotate(-180f)
                        .width(135.dp)
                        .fillMaxWidth()
                    else Modifier
                        .width(135.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.FillBounds,
                    colorFilter = ColorFilter.tint(if (isDark) darkIcon else lightIcon)
                )
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                {
                    Spacer(modifier = Modifier.weight(0.5f))
                    MyButton(
                        text = buttonText,
                        onClick = { },
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.weight(0.5f))

                }
            }
        }
    }
}

@Preview(name = "dark", uiMode = UI_MODE_NIGHT_YES, locale = "ar")
@Preview(name = "light", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewRatingCard() {
    RentafieldTheme {
        RatingCard(
            buttonText = R.string.ratings,
            mainText = "Was a perfect match",
            subText = "Rating the playgrounds",
            timeIcon = R.drawable.clock
        )
    }

}