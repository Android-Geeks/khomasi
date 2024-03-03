package com.company.khomasi.presentation.components.cards

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.theme.KhomasiTheme

@Composable
fun RatingCard(
    @StringRes buttonText: Int,
    modifier: Modifier = Modifier,
    @DrawableRes timeIcon: Int = 0,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(111.dp)
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = MaterialTheme.shapes.large
            )
    ) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
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
                        onClick = {  },
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier
                        )
                    Spacer(modifier = Modifier.weight(0.5f))

                }
            }
            Spacer(modifier = Modifier.weight(0.9f))
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = "rating",
                    style = MaterialTheme.typography.displayLarge,
                )
                Row {

                    Text(
                        text = "time",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    if (timeIcon != 0) {
                        Icon(
                            painter = painterResource(timeIcon),
                            contentDescription = " ",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 6.dp, start = 4.dp)
                        )
                    }
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
            timeIcon = R.drawable.clock
        )
    }

}