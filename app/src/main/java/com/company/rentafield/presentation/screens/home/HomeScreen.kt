package com.company.rentafield.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.rentafield.presentation.components.connectionStates.ThreeBounce
import com.company.rentafield.presentation.screens.home.components.HomeContent
import com.company.rentafield.presentation.screens.home.components.HomeSearchBar
import com.company.rentafield.presentation.screens.home.components.UserProfileHeader
import com.company.rentafield.presentation.screens.home.model.HomeUiState
import com.company.rentafield.theme.RentafieldTheme
import com.company.rentafield.utils.ThemePreviews
import kotlinx.coroutines.flow.StateFlow


@Composable
fun HomeScreen(
    homeUiState: StateFlow<HomeUiState>,
    onClickUserImage: () -> Unit,
    onClickBell: () -> Unit,
    onSearchBarClicked: () -> Unit,
    onClickViewAll: () -> Unit,
    onAdClicked: (String) -> Unit,
    onClickPlaygroundCard: (Int, Boolean) -> Unit,
    onFavouriteClick: (Int) -> Unit
) {
    val uiState by homeUiState.collectAsStateWithLifecycle()
    val adsList = remember { uiState.adList }
    val displayedPlaygrounds =
        remember { derivedStateOf { uiState.playgrounds.sortedBy { it.distance }.take(3) } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        UserProfileHeader(
            userFirstName = uiState.userName,
            profileImage = uiState.profileImage,
            onClickUserImage = onClickUserImage,
            onClickBell = onClickBell
        )

        HomeSearchBar(onSearchBarClicked = onSearchBarClicked)

        HomeContent(
            playgroundsData = displayedPlaygrounds.value,
            adsList = adsList,
            homeUiState = uiState,
            userId = uiState.userId,
            onAdClicked = onAdClicked,
            onClickViewAll = { onClickViewAll() },
            onClickPlaygroundCard = onClickPlaygroundCard,
            onFavouriteClick = { playgroundId -> onFavouriteClick(playgroundId) }
        )
        if (uiState.playgrounds.isEmpty()) {
            ThreeBounce(
                color = MaterialTheme.colorScheme.primary,
                size = DpSize(75.dp, 75.dp),
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


@ThemePreviews
@Composable
fun HomeScreenPreview() {
    RentafieldTheme {
        val mockViewModel: MockHomeViewModel = hiltViewModel()
        HomeScreen(
            homeUiState = mockViewModel.homeUiState,
            onClickUserImage = { },
            onClickBell = { },
            onSearchBarClicked = {},
            onClickViewAll = { },
            onAdClicked = { },
            onClickPlaygroundCard = { _, _ -> },
            onFavouriteClick = {}
        )
    }
}
