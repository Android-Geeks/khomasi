package com.company.rentafield.presentation.playground.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.company.rentafield.R
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.playground.PlaygroundScreenResponse
import com.company.rentafield.theme.darkText
import com.company.rentafield.theme.lightText
import kotlinx.coroutines.flow.StateFlow

@Composable
fun PlaygroundRules(
    playgroundStateFlow: StateFlow<DataState<PlaygroundScreenResponse>>,
) {
    val playgroundState by playgroundStateFlow.collectAsStateWithLifecycle()
    var playgroundData by remember { mutableStateOf<PlaygroundScreenResponse?>(null) }

    if (playgroundState is DataState.Success) {
        playgroundData = (playgroundState as DataState.Success).data
    }

    if (playgroundData != null) {
        val rulesList = playgroundData!!.playground.rules.split(",")
        val myHeight = (50.dp * rulesList.size).coerceAtLeast(50.dp)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(myHeight)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.field_instructions),
                style = MaterialTheme.typography.titleLarge,
                color = if (isSystemInDarkTheme()) darkText else lightText
            )

            for (i in rulesList.indices.take(6)) {
                Text(
                    text = " ${i + 1}. ${rulesList[i]}",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }
        }
    }
}