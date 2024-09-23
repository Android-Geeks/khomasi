package com.company.rentafield.presentation.screens.playground.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.rentafield.R
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.playground.PlaygroundScreenResponse
import com.company.rentafield.domain.model.playground.PlaygroundX
import com.company.rentafield.presentation.theme.RentafieldTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun PlaygroundDescription(
    playgroundStateFlow: StateFlow<DataState<PlaygroundScreenResponse>>,
) {
    val playgroundState by playgroundStateFlow.collectAsStateWithLifecycle()
    var playgroundData by remember { mutableStateOf<PlaygroundScreenResponse?>(null) }

    if (playgroundState is DataState.Success) {
        playgroundData = (playgroundState as DataState.Success).data
    }
    val description = playgroundData?.playground?.description ?: ""
    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.field_description),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = description,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.tertiary
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
fun PlaygroundDescriptionPreview() {
    RentafieldTheme {
        PlaygroundDescription(
            playgroundStateFlow = MutableStateFlow(
                DataState.Success(
                    PlaygroundScreenResponse(
                        playgroundPictures = listOf(),
                        playground = PlaygroundX(
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
            )
        )
    }
}
