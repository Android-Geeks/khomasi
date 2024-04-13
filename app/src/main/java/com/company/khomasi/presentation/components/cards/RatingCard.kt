package com.company.khomasi.presentation.components.cards

import android.content.res.Configuration.UI_MODE_NIGHT_NO
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.theme.darkOverlay
import com.company.khomasi.theme.darkText
import com.company.khomasi.theme.lightOverlay
import com.company.khomasi.theme.lightText

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
                    .padding(horizontal = 8.dp)
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
                            contentDescription = " ",
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
            Spacer(modifier = Modifier.weight(0.9f))

            Box(modifier = Modifier) {
                Image(
                    painter = painterResource(id = R.drawable.group_lines),
                    contentDescription = null,
                    modifier = Modifier
                        .width(95.dp)
                        .fillMaxSize()
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

@Preview(name = "dark", uiMode = UI_MODE_NIGHT_YES)
@Preview(name = "light", uiMode = UI_MODE_NIGHT_NO)

@Composable
fun PreviewRatingCard() {
    KhomasiTheme {
        RatingCard(
            buttonText = R.string.ratings,
            mainText = "Was a perfect match",
            subText = "Rating the playgrounds",
            timeIcon = R.drawable.clock
        )
    }

}