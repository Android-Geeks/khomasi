package com.company.rentafield.presentation.screens.venues


import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.rentafield.R
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.LocalUser
import com.company.rentafield.domain.model.search.FilteredPlaygroundResponse
import com.company.rentafield.presentation.components.SubScreenTopBar
import com.company.rentafield.presentation.components.cards.PlaygroundCard
import com.company.rentafield.presentation.components.connectionStates.ThreeBounce
import com.company.rentafield.theme.RentafieldTheme
import kotlinx.coroutines.flow.StateFlow

@Composable
fun BrowsePlaygrounds(
    localUser: StateFlow<LocalUser>,
    browseUiState: StateFlow<BrowseUiState>,
    filteredPlayground: StateFlow<DataState<FilteredPlaygroundResponse>>,
    getFilteredPlaygrounds: () -> Unit,
    onFilterClick: () -> Unit,
    onFavouriteClicked: (Int) -> Unit,
    onClickPlaygroundCard: (Int, Boolean) -> Unit
) {
    val user by localUser.collectAsStateWithLifecycle()
    val uiState by browseUiState.collectAsStateWithLifecycle()
    val filteredPlaygrounds by filteredPlayground.collectAsStateWithLifecycle()
    val showLoading = filteredPlaygrounds is DataState.Loading


    LaunchedEffect(user) {
        getFilteredPlaygrounds()
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                SubScreenTopBar(
                    title = stringResource(id = R.string.browse_fields),
                    onBackClick = {},
                    navigationIcon = {},
                    actions = {
                        IconButton(
                            onClick = onFilterClick
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.sliders),
                                contentDescription = stringResource(id = R.string.filter_results)
                            )
                        }
                    }
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = it.calculateTopPadding())
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                items(uiState.playgrounds) { playground ->
                    PlaygroundCard(
                        playground = playground,
                        onFavouriteClick = {
                            onFavouriteClicked(playground.id)
                        },
                        onViewPlaygroundClick = {
                            onClickPlaygroundCard(
                                playground.id,
                                playground.isFavourite
                            )
                        },
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

            }
        }
        if (showLoading) {
            ThreeBounce(modifier = Modifier.fillMaxSize())
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
fun BrowsePreview() {
    RentafieldTheme {
        val mockBrowseViewModel = MockBrowseViewModel()
        BrowsePlaygrounds(
            localUser = mockBrowseViewModel.localUser,
            browseUiState = mockBrowseViewModel.uiState,
            filteredPlayground = mockBrowseViewModel.filteredPlaygrounds,
            getFilteredPlaygrounds = { },
            onFilterClick = { },
            onFavouriteClicked = { },
            onClickPlaygroundCard = { _, _ -> }
        )
    }
}