package com.company.rentafield.presentation.screens.playground.components

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.rentafield.R
import com.company.rentafield.domain.DataState
import com.company.rentafield.presentation.components.cards.CommentCard
import com.company.rentafield.presentation.screens.playground.MockPlaygroundViewModel
import com.company.rentafield.presentation.theme.RentafieldTheme


@Composable
fun PlaygroundReviews(
    reviews: DataState<com.company.rentafield.data.models.playground.PlaygroundReviewsResponse>,
    onClickCancel: () -> Unit
) {
    val reviewsList = remember {
        mutableStateOf<List<com.company.rentafield.data.models.playground.Review>>(emptyList())
    }

    LaunchedEffect(reviews) {
        reviewsList.value = if (reviews is DataState.Success) {
            reviews.data.reviewList
        } else {
            mutableListOf()
        }
        Log.d("PlaygroundReviews", "PlaygroundReviews: $reviews")

    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.all_ratings) +
                            " (${reviewsList.value.size}) " +
                            stringResource(R.string.rate),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.displayMedium
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { onClickCancel() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.xcircle),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        items(reviewsList.value.size) { review ->
            CommentCard(
                userName = reviewsList.value[review].userName,
                userImageUrl = reviewsList.value[review].userPicture ?: "",
                comment = reviewsList.value[review].comment,
                dateTime = reviewsList.value[review].reviewTime,
                rating = reviewsList.value[review].rating.toFloat()
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PlaygroundReviewsPreview() {
    RentafieldTheme {
        val mockViewModel =
            MockPlaygroundViewModel()
        PlaygroundReviews(
            reviews = mockViewModel.reviewsState.collectAsState().value,
            onClickCancel = {}
        )
    }
}