package com.company.rentafield.presentation.screens.playground.components

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.rentafield.R
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.playground.PlaygroundScreenResponse
import com.company.rentafield.presentation.components.MyOutlinedButton
import com.company.rentafield.presentation.screens.playground.PlaygroundUiState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun PlaygroundRatesAndReviews(
    uiState: StateFlow<PlaygroundUiState>,
    playgroundStateFlow: StateFlow<DataState<PlaygroundScreenResponse>>,
    onViewRatingClicked: () -> Unit
) {
    val playgroundState by playgroundStateFlow.collectAsStateWithLifecycle()
    var playgroundData by remember { mutableStateOf<PlaygroundScreenResponse?>(null) }

    if (playgroundState is DataState.Success) {
        playgroundData = (playgroundState as DataState.Success).data
    }
    val ui by uiState.collectAsStateWithLifecycle()

    val rate = playgroundData?.playground?.rating.toString()
    val rateNum = ui.reviewsCount
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
                    style = MaterialTheme.typography.titleLarge
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
                        text = rate.take(3), style = MaterialTheme.typography.bodyLarge
                    )
                    Icon(
                        painter = painterResource(R.drawable.unfilled_star),
                        contentDescription = null
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