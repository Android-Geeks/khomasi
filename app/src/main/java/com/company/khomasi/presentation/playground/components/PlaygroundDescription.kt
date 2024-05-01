package com.company.khomasi.presentation.playground.components

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.khomasi.R
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.PlaygroundScreenResponse
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
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = description,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}