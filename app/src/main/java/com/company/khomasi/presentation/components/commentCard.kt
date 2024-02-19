package com.company.khomasi.presentation.components

import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.company.khomasi.theme.lightPrimary
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextAlign

@Composable
fun CommentCard ( commentDetails: CommentDetails){

    Box(
        modifier = Modifier
            .width(358.dp)
            .background(color = MaterialTheme.colorScheme.surface)
    ){
        Column(modifier = Modifier.padding(8.dp)){

            Row (verticalAlignment = Alignment.CenterVertically,
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
            tint = lightPrimary,
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
                "data:image/png;base64,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",
                "very good playground",
                "19 May 2024",
                "2"
            )
        )
    }
}

