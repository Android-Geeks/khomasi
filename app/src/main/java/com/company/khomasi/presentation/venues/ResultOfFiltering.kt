package com.company.khomasi.presentation.venues

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.khomasi.presentation.components.cards.PlaygroundCard
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ResultOfFiltering(
    browseUiState: StateFlow<BrowseUiState>,
) {
    val uiState by browseUiState.collectAsStateWithLifecycle()
    LaunchedEffect(uiState) {
        Log.d("BrowsePlaygroundsViewModel", "onShowFiltersClicked: ${uiState.playgroundsResult}")

    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        items(uiState.playgroundsResult) { playground ->
            PlaygroundCard(
                playground = playground,
                onFavouriteClick = {},
                onViewPlaygroundClick = {},
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
