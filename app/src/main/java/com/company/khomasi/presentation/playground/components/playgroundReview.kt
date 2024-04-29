package com.company.khomasi.presentation.playground.components

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.company.khomasi.R
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.PlaygroundReviewResponse
import com.company.khomasi.presentation.components.cards.CommentCard
import com.company.khomasi.presentation.playground.PlaygroundViewModel
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.theme.darkText
import com.company.khomasi.theme.lightText
import kotlinx.coroutines.flow.StateFlow


@Composable
fun PlaygroundReviews(
    getPlaygroundReviews: () -> Unit,
    reviewsState: StateFlow<DataState<PlaygroundReviewResponse>>
) {
    val reviews = reviewsState.collectAsState().value
    LaunchedEffect(Unit) {
        getPlaygroundReviews()
    }
    LaunchedEffect(reviews) {
        Log.d("pook", "PlaygroundReviews: $reviews")
    }
    if (reviews is DataState.Success) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)
        ) {
            val reviewList = reviews.data.reviewList
            item {
                Text(
                    text = stringResource(R.string.all_ratings) +
                            " (${reviewList.size}) " +
                            stringResource(R.string.rate),
                    color = if (isSystemInDarkTheme()) darkText else lightText,
                    style = MaterialTheme.typography.displayMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            items(reviewList.size) { review ->
                CommentCard(
                    userName = reviewList[review].userName,
                    userImageUrl = reviewList[review].userPicture,
                    comment = reviewList[review].comment,
                    dateTime = reviewList[review].reviewTime,
                    rating = reviewList[review].rating.toFloat()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }


}

@Preview
@Composable
fun PlaygroundReviewsPreview() {
    KhomasiTheme {
        val vm: PlaygroundViewModel = hiltViewModel()
        PlaygroundReviews(
            getPlaygroundReviews = { vm.getPlaygroundReviews() },
            reviewsState = vm.reviewsState
        )
    }
}