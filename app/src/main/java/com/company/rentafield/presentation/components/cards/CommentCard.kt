package com.company.rentafield.presentation.components.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.company.rentafield.R
import com.company.rentafield.theme.KhomasiTheme
import com.company.rentafield.utils.convertToBitmap
import com.company.rentafield.utils.extractDateFromTimestamp
import com.company.rentafield.utils.parseTimestamp
import com.gowtham.ratingbar.RatingBar

@Composable
fun CommentCard(
    userName: String,
    userImageUrl: String?,
    comment: String,
    dateTime: String,
    rating: Float,
) {
    val date = extractDateFromTimestamp(parseTimestamp(dateTime), format = "dd MMMM yyyy")
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(userImageUrl?.convertToBitmap() ?: "").crossfade(true).build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    loading = { CircularProgressIndicator() },
                    error = {
                        Image(
                            painter = painterResource(id = R.drawable.user_img),
                            contentDescription = null
                        )
                    },
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.background)
                )

                Column(
                    Modifier.padding(start = 4.dp), verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = userName,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier

                    )
                    Text(
                        text = date,
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Start
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
                RatingBar(
                    value = rating,
                    size = 18.dp,
                    spaceBetween = 1.dp,
                    painterEmpty = painterResource(id = R.drawable.unfilled_star),
                    painterFilled = painterResource(id = R.drawable.filled_star),
                    onValueChange = {},
                    onRatingChanged = {},
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .align(Alignment.Top)
                )
            }
            Text(
                text = comment,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

    }
}


@Preview
@Composable
fun CommentCardPreview() {
    KhomasiTheme {
        CommentCard(
            userName = "Ali Gamal",
            userImageUrl = "",
            comment = "very good playground",
            dateTime = "2024-03-14T18:01:12.696",
            rating = 3.7f

        )
    }
}

