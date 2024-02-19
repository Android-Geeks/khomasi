package com.company.khomasi.presentation.components

import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextAlign

@Composable
fun CommentCard ( commentDetails: CommentDetails){

    Card(
        modifier = Modifier.fillMaxWidth()
    ){
        Column(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ){
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ){
                AsyncImage(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(50.dp)),
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

                Row(
                    modifier = Modifier.size(width = 96.dp, height = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                      for(i in 1..5)
                          RatingStar()
                }
            }
            Text(
                text = commentDetails.comment,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start)
        }

    }
}

@Composable
fun RatingStar(isClicked : Boolean = false){

    var clicked by remember {
        mutableStateOf(isClicked)
    }
    IconButton(
        onClick = { clicked = !clicked},
        modifier = Modifier.size(16.dp),
    ) {
        Icon(
            painter = painterResource(id = if (clicked) R.drawable.star_1 else R.drawable.star),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(16.dp)
        )
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
                "2"
            )
        )
    }
}

