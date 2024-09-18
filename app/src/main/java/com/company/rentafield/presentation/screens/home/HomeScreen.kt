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
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.LocalUser
import com.company.rentafield.domain.model.playground.Playground
import com.company.rentafield.presentation.components.connectionStates.ThreeBounce
import com.company.rentafield.presentation.screens.home.components.HomeContent
import com.company.rentafield.presentation.screens.home.components.HomeSearchBar
import com.company.rentafield.presentation.screens.home.components.UserProfileHeader
import com.company.rentafield.presentation.screens.home.model.HomeUiState
import com.company.rentafield.theme.RentafieldTheme
import com.company.rentafield.utils.ThemePreviews
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


@Composable
fun HomeScreen(
    playgroundsState: StateFlow<List<Playground>>,
    localUserState: StateFlow<LocalUser>,
    homeUiState: StateFlow<HomeUiState>,
    onClickUserImage: () -> Unit,
    onClickBell: () -> Unit,
    onSearchBarClicked: () -> Unit,
    onClickViewAll: () -> Unit,
    onAdClicked: (String) -> Unit,
    onClickPlaygroundCard: (Int, Boolean) -> Unit,
    onFavouriteClick: (Int) -> Unit
) {
    val localUser by localUserState.collectAsStateWithLifecycle()
    val playgrounds by playgroundsState.collectAsStateWithLifecycle()
    val uiState by homeUiState.collectAsStateWithLifecycle()
    val displayedPlaygrounds =
        remember { derivedStateOf { playgrounds.sortedBy { it.distance }.take(3) } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        UserProfileHeader(
            userFirstName = localUser.firstName ?: "",
            profileImage = uiState.profileImage,
            onClickUserImage = onClickUserImage,
            onClickBell = onClickBell
        )

        HomeSearchBar(onSearchBarClicked = onSearchBarClicked)

        HomeContent(
            playgroundsData = displayedPlaygrounds.value,
            homeUiState = uiState,
            userId = localUser.userID ?: "",
            onAdClicked = onAdClicked,
            onClickViewAll = { onClickViewAll() },
            onClickPlaygroundCard = onClickPlaygroundCard,
            onFavouriteClick = { playgroundId -> onFavouriteClick(playgroundId) }
        )
        if (playgrounds.isEmpty()) {
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
            playgroundsState = MutableStateFlow(
                (mockViewModel.playgroundState.value as? DataState.Success)?.data?.playgrounds
                    ?: emptyList()
            ),
            localUserState = MutableStateFlow(LocalUser()),
            homeUiState = mockViewModel.homeUiState,
            onClickUserImage = { },
            onClickBell = { },
            onSearchBarClicked = {},
            onClickViewAll = { mockViewModel.onClickViewAll() },
            onAdClicked = { },
            onClickPlaygroundCard = { _, _ -> },
            onFavouriteClick = {}
        )
    }
}
