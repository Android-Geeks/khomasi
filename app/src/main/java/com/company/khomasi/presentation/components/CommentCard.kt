package com.company.khomasi.presentation.components

import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.company.khomasi.R
import com.company.khomasi.theme.KhomasiTheme
import androidx.compose.ui.text.style.TextAlign
import com.gowtham.ratingbar.RatingBar

@Composable
fun CommentCard(commentDetails: CommentDetails) {
    Card(
        modifier = Modifier.fillMaxWidth()
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
                AsyncImage(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(commentDetails.userImageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
//                    error = painterResource(id = R.drawable.ic_broken_image),
                    placeholder = painterResource(id = R.drawable.user_img)
                )

                Column(
                    Modifier.padding(start = 4.dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = commentDetails.userName,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier

                    )
                    Text(
                        text = commentDetails.date,
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Start
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
                RatingBar(
                    value = commentDetails.rating,
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
                text = commentDetails.comment,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(top = 8.dp)
            )
        }

    }
}


@Preview
@Composable
fun CommentCardPreview() {
    KhomasiTheme {
        CommentCard(
            commentDetails = CommentDetails(
                "Ali Gamal",
                "",
                "very good playground",
                "19 May 2024",
                "2",
                3.7f
            )
        )
    }
}

