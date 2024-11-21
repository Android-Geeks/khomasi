package com.company.rentafield.presentation.screens.playground.components

import android.content.Context
import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.rentafield.R
import com.company.rentafield.domain.DataState
import com.company.rentafield.presentation.theme.RentafieldTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun PlaygroundDefinition(
    playgroundStateFlow: StateFlow<DataState<com.company.rentafield.domain.models.playground.PlaygroundScreenResponse>>,
    context: Context = LocalContext.current
) {
    val playgroundState by playgroundStateFlow.collectAsStateWithLifecycle()
    var playgroundData by remember {
        mutableStateOf<com.company.rentafield.domain.models.playground.PlaygroundScreenResponse?>(
            null
        )
    }

    if (playgroundState is DataState.Success) {
        playgroundData = (playgroundState as DataState.Success).data
    }
    val name = playgroundData?.playground?.name ?: ""
    val openingTime = playgroundData?.playground?.openingHours ?: ""
    val address = playgroundData?.playground?.address ?: ""
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(94.dp)
        ) {
            Row(
                Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PlaygroundStatus(
                    status = stringResource(id = R.string.open_now)
                )
            }
        }
        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        ) {
            Text(
                text = name, style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            IconWithText(text = openingTime, iconId = R.drawable.clock)

            IconWithText(text = address, iconId = R.drawable.mappin)

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(32.dp), horizontalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 93.dp, end = 109.dp)
                        .clickable {
                            val lat = playgroundData?.playground?.latitude ?: 0.0
                            val long = playgroundData?.playground?.longitude ?: 0.0
                            val zoom = 10.0f

                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse("geo:$lat,$long?z=$zoom&q=$lat,$long(Marker)")
                            context.startActivity(intent)
                        },
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onPrimaryContainer),
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painterResource(id = R.drawable.logos_google_maps),
                            contentDescription = null,
                            modifier = Modifier.size(width = 11.dp, height = 16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = stringResource(R.string.Show_on_map),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.background,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 3.dp)
                        )
                    }
                }
            }
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
fun PlaygroundDefinitionPreview() {
    RentafieldTheme {
        PlaygroundDefinition(
            playgroundStateFlow = MutableStateFlow(
                DataState.Success(
                    com.company.rentafield.domain.models.playground.PlaygroundScreenResponse(
                        playgroundPictures = listOf(),
                        playground = com.company.rentafield.domain.models.playground.PlaygroundX(
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