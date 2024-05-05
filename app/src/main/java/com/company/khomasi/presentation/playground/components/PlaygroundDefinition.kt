package com.company.khomasi.presentation.playground.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.khomasi.R
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.PlaygroundScreenResponse
import com.company.khomasi.presentation.playground.IconWithText
import com.company.khomasi.theme.darkText
import com.company.khomasi.theme.lightText
import kotlinx.coroutines.flow.StateFlow

@Composable
fun PlaygroundDefinition(
    playgroundStateFlow: StateFlow<DataState<PlaygroundScreenResponse>>,
    onClickDisplayOnMap: () -> Unit
) {
    val playgroundState by playgroundStateFlow.collectAsStateWithLifecycle()
    var playgroundData by remember { mutableStateOf<PlaygroundScreenResponse?>(null) }

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
                        .clickable { onClickDisplayOnMap() },
                    colors = CardDefaults.cardColors(if (isSystemInDarkTheme()) darkText else lightText)
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
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }

}