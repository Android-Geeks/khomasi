package com.company.khomasi.presentation.components.cards

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.iconButtons.FavoriteIcon
import com.company.khomasi.theme.KhomasiTheme

@Composable
fun PlaygroundCard(
    playground: Playground,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = MaterialTheme.shapes.large
            )
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(131.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(62.dp)
                ) {
                    FavoriteIcon(
                        onFavoriteClick = {  },
                        isFavorite = false,
                        modifier = Modifier.padding(top = 12.dp, start = 6.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    val bookingText =
                        if (playground.isBookable) stringResource(id = R.string.bookable)
                        else stringResource(id = R.string.un_bookable)
                    Text(
                        text = bookingText,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .clip(RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp))
                            .background(
                                color = MaterialTheme.colorScheme.background
                            )
                            .padding(start = 5.dp, end = 5.dp, top = 5.dp, bottom = 5.dp)
                    )

                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = playground.name,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.weight(2f))

                Text(
                    text = playground.rating.toString(),
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .weight(0.2f)
                        .padding(top = 8.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.unfilled_star),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 13.dp, start = 4.dp)
                )

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.mappin),
                    contentDescription = null,
                    modifier = Modifier.padding(top = 7.dp, end = 4.dp)

                )
                Text(
                    text = playground.address,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                )


            }
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                thickness = 1.dp
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    painter = painterResource(id = R.drawable.currencycircledollar),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 4.dp, top = 12.dp)
                )
                Text(
                    text = playground.price + " / ",
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(top = 6.dp)
                )

                Text(
                    text = playground.openingHours,
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(top = 6.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                MyButton(
                    text = R.string.view_playground,
                    onClick = { },
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = MaterialTheme.shapes.medium
                        )
                        .width(171.dp)
                        .height(48.dp)
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
                imageUrl = "https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg",
                rating = 4.9f,
                price = "price",
                openingHours = "hour",
                isFavorite = false,
                isBookable = false
            )

        )
    }
}