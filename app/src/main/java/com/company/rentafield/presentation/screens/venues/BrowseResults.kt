package com.company.rentafield.presentation.screens.venues

import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.rentafield.R
import com.company.rentafield.presentation.components.SubScreenTopBar
import com.company.rentafield.presentation.components.cards.PlaygroundCard
import com.company.rentafield.presentation.screens.venues.component.EmptyResult
import com.company.rentafield.theme.RentafieldTheme
import kotlinx.coroutines.flow.StateFlow

@Composable
fun BrowseResults(
    browseUiState: StateFlow<BrowseUiState>,
    onBackClicked: () -> Unit,
    onFavClicked: (Int) -> Unit,
    onClickPlayground: (Int, Boolean) -> Unit,
    context: Context = LocalContext.current,
    isDark: Boolean = isSystemInDarkTheme()
) {
    val uiState by browseUiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            SubScreenTopBar(
                title = stringResource(id = R.string.browse_results),
                onBackClick = onBackClicked,
            )
        }
    ) { paddingValues ->
        if (uiState.playgroundsResult.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                item {
                    Text(
                        text = context.getString(
                            R.string.found_fields_count,
                            uiState.playgroundsResult.size
                        ),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                items(uiState.playgroundsResult) { playground ->
                    PlaygroundCard(
                        playground = playground,
                        onFavouriteClick = { onFavClicked(playground.id) },
                        onViewPlaygroundClick = {
                            onClickPlayground(
                                playground.id,
                                playground.isFavourite
                            )
                        },
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        } else {
            EmptyResult(onClick = onBackClicked, isDark = isDark)
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
fun BrowseResultsPreview() {
    RentafieldTheme {
        val mockBrowseViewModel = MockBrowseViewModel()
        BrowseResults(
            browseUiState = mockBrowseViewModel.uiState,
            onBackClicked = {},
            onFavClicked = {},
            onClickPlayground = { _, _ -> }
        )
    }
}