package com.company.khomasi.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.theme.KhomasiTheme

@Composable
fun PlayGround(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(265.dp)
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = MaterialTheme.shapes.large
            )
            .padding(8.dp)
    ) {

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            Box {
                Image(
                    painter = painterResource(id = R.drawable.playground),
                    contentDescription = " ",
                    modifier = modifier
                        .fillMaxWidth()
                        .height(131.dp),
                    contentScale = ContentScale.FillWidth
                )
                Row(
                    modifier = modifier
                        .height(62.dp)
                ) {
                    Column {
                        Spacer(modifier = modifier.height(15.dp))
                        Box(
                            modifier = modifier
                                .height(32.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.background
                                )
                                .padding(start = 6.dp, top = 3.dp, end = 4.dp, bottom = 3.dp)
                        )

                        {
                            Text(
                                text = "booking",
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                    Spacer(modifier = modifier.weight(1f))

                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.heart),
                            contentDescription = " ",
                            tint = Color.White,
                            modifier = modifier.padding(0.125.dp)
                        )
                    }

                }
            }
            Row(modifier = modifier.fillMaxWidth()) {
                Text(
                    text = "4.9",
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = modifier
                        .weight(0.2f)
                        .padding(top = 8.dp)


                )
                Icon(
                    painter = painterResource(id = R.drawable.star),
                    contentDescription = " ",
                    modifier = modifier
                        .padding(top = 13.dp, start = 4.dp)
                )
                Spacer(modifier = modifier.weight(2f))
                Text(
                    text = "playground",
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = modifier.weight(1f)
                )

            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Spacer(modifier = modifier.weight(2f))
                Text(
                    text = "location",
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = modifier
                )
                Icon(
                    painter = painterResource(id = R.drawable.mappin),
                    contentDescription = "",
                    modifier = modifier.padding(top = 7.dp, start = 4.dp)

                )
            }
            Divider(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                ,
                thickness = 1.dp
            )
            Row(modifier = modifier.fillMaxWidth()) {
                MyButton(
                    text = R.string.my_bookings,
                    onClick = { /*TODO*/ },
                    modifier = modifier
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(horizontal = 34.dp)
                        .width(171.dp)
                        .height(38.dp)

                )

                Spacer(modifier = modifier.weight(1f))
                Text(text = "price",
                    textAlign = TextAlign.End,
                    modifier = modifier.padding(top = 6.dp))
                Icon(
                    painter = painterResource(id = R.drawable.currencycircledollar),
                    contentDescription = " ",
                    modifier =modifier.padding(start = 4.dp, top = 11.dp)
                )
            }
        }
    }

}


@Preview(name = "light", showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "dark", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewCard() {
    KhomasiTheme {
        PlayGround(
        )
    }
}
