package com.company.khomasi.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.company.khomasi.R
import com.company.khomasi.theme.KhomasiTheme

@Composable
fun PlaygroundCard(
    playground: Playground,
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
                AsyncImage(
                    model = ImageRequest
                        .Builder(context = LocalContext.current)
                        .data(playground.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    placeholder = painterResource(id = R.drawable.playground),
                    modifier = modifier
                        .fillMaxWidth()
                        .height(131.dp)
                )
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(62.dp)
                ) {
                    val bookingText =
                        if (playground.isBookable) stringResource(id = R.string.bookable)
                        else stringResource(id = R.string.unbookable)
                    Text(
                        text = bookingText,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = modifier
                            .padding(top = 12.dp)
                            .clip(RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp))
                            .background(
                                color = MaterialTheme.colorScheme.background
                            )
                            .padding(start = 5.dp, end = 5.dp, top = 5.dp, bottom = 5.dp)
                    )
                    Spacer(modifier = modifier.weight(1f))
                    FavoriteIcon(
                        onFavoriteClick = { /*TODO*/ },
                        isFavorite = false,
                        modifier = Modifier.padding(top = 12.dp, end = 6.dp)
                    )
                }
            }
            Row(modifier = modifier.fillMaxWidth()) {
                Text(
                    text = playground.rating.toString(),
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = modifier
                        .weight(0.2f)
                        .padding(top = 8.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.unfilled_star),
                    contentDescription = null,
                    modifier = modifier
                        .padding(top = 13.dp, start = 4.dp)
                )
                Spacer(modifier = modifier.weight(2f))
                Text(
                    text = playground.name,
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
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
                    text = playground.address,
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = modifier
                )
                Icon(
                    painter = painterResource(id = R.drawable.mappin),
                    contentDescription = null,
                    modifier = modifier.padding(top = 7.dp, start = 4.dp)

                )
            }
            HorizontalDivider(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                thickness = 1.dp
            )
            Row(modifier = modifier.fillMaxWidth()) {
                MyButton(
                    text = R.string.view_playground,
                    onClick = { },
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
                Text(
                    text = playground.price,
                    textAlign = TextAlign.End,
                    modifier = modifier.padding(top = 6.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.currencycircledollar),
                    contentDescription = null,
                    modifier = modifier.padding(start = 4.dp, top = 11.dp)
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
        PlaygroundCard(
            playground = Playground(
                name = "playground",
                address = "location",
                imageUrl = "https://www.pinterest.com/pin/339810734383350441/",
                rating = 4.9f,
                price = "price",
                openingHours = "hour",
                isFavorite = false,
                isBookable = false
            )

        )
    }
}