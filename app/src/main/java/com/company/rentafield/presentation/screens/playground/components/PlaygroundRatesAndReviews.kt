package com.company.rentafield.presentation.screens.playground.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.rentafield.R
import com.company.rentafield.domain.DataState
import com.company.rentafield.presentation.components.MyOutlinedButton
import com.company.rentafield.presentation.screens.playground.model.PlaygroundReviewsUiState
import com.company.rentafield.presentation.theme.RentafieldTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun PlaygroundRatesAndReviews(
    reviewsUiState: StateFlow<PlaygroundReviewsUiState>,
    playgroundStateFlow: StateFlow<DataState<com.company.rentafield.data.models.playground.PlaygroundScreenResponse>>,
    onViewRatingClicked: () -> Unit
) {
    val playgroundState by playgroundStateFlow.collectAsStateWithLifecycle()
    var playgroundData by remember {
        mutableStateOf<com.company.rentafield.data.models.playground.PlaygroundScreenResponse?>(
            null
        )
    }

    if (playgroundState is DataState.Success) {
        playgroundData = (playgroundState as DataState.Success).data
    }
    val reviews by reviewsUiState.collectAsStateWithLifecycle()

    val rate =
        remember(playgroundData?.playground?.rating) { playgroundData?.playground?.rating.toString() }
    val rateNum = remember(reviews) { reviews.reviewsCount }
    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row {
                Text(
                    text = stringResource(id = R.string.ratings),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "($rateNum ${stringResource(id = R.string.rate)})",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    Text(
                        text = rate.take(3),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Icon(
                        painter = painterResource(R.drawable.unfilled_star),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }
            }

            MyOutlinedButton(
                text = R.string.view_ratings,
                onClick = onViewRatingClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Preview(
    name = "DARK | EN",
    locale = "en",
    uiMode = UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF0E0E0E,
    showBackground = true
)
@Preview(
    name = "LIGHT | AR",
    locale = "ar",
    uiMode = UI_MODE_NIGHT_NO,
    backgroundColor = 0xFFF5F5F5,
    showBackground = true
)
@Composable
fun PlaygroundRatesAndReviewsPreview() {
    RentafieldTheme {
        PlaygroundRatesAndReviews(
            reviewsUiState = MutableStateFlow(
                PlaygroundReviewsUiState(
                    reviewsCount = 5,
                    showReviews = false
                )
            ),
            playgroundStateFlow = MutableStateFlow(
                DataState.Success(
                    com.company.rentafield.data.models.playground.PlaygroundScreenResponse(
                        playgroundPictures = listOf(),
                        playground = com.company.rentafield.data.models.playground.PlaygroundX(
                            id = 2,
                            name = "Adventure Island",
                            description = "A thrilling playground with exciting obstacle courses and climbing structures.",
                            advantages = "Challenging activities, promotes physical fitness, great for older kids.",
                            address = "456 Elm Street, Townsville",
                            type = 2,
                            rating = 4.5,
                            country = "Canada",
                            city = "Townsville",
                            latitude = 43.6532,
                            longitude = -79.3832,
                            holidays = "Thanksgiving Day, Boxing Day",
                            openingHours = "9:00 AM - 7:00 PM",
                            feesForHour = 25,
                            cancellationFees = 10,
                            isBookable = true,
                            rules = "Wear appropriate footwear, adult supervision required for children under 10."
                        )
                    )
                )
            ),
            onViewRatingClicked = {}
        )
    }
}
