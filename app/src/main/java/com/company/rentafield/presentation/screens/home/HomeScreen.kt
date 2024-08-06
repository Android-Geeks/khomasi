package com.company.rentafield.presentation.screens.home

import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.rentafield.R
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.LocalUser
import com.company.rentafield.domain.model.playground.Playground
import com.company.rentafield.domain.model.playground.PlaygroundsResponse
import com.company.rentafield.presentation.components.AdsContent
import com.company.rentafield.presentation.components.AdsSlider
import com.company.rentafield.presentation.components.cards.PlaygroundCard
import com.company.rentafield.presentation.components.connectionStates.ThreeBounce
import com.company.rentafield.theme.RentafieldTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


@Composable
fun HomeScreen(
    playgroundsState: StateFlow<DataState<PlaygroundsResponse>>,
    localUserState: StateFlow<LocalUser>,
    homeUiState: StateFlow<HomeUiState>,
    onClickUserImage: () -> Unit,
    onClickBell: () -> Unit,
    onSearchBarClicked: () -> Unit,
    onClickViewAll: () -> Unit,
    onAdClicked: (String) -> Unit,
    onClickPlaygroundCard: (Int, Boolean) -> Unit,
    onFavouriteClick: (Int) -> Unit,
    getHomeScreenData: () -> Unit,
    getUserData: () -> Unit,
) {
    val localUser by localUserState.collectAsStateWithLifecycle()
    val playgrounds by playgroundsState.collectAsStateWithLifecycle()
    val uiState by homeUiState.collectAsStateWithLifecycle()

    var showLoading by remember { mutableStateOf(false) }
    var playgroundsData by remember { mutableStateOf(listOf<Playground>()) }

    LaunchedEffect(playgrounds) {
        showLoading = playgrounds is DataState.Loading || playgrounds is DataState.Empty
        playgroundsData = (playgrounds as? DataState.Success)?.data?.playgrounds ?: emptyList()
//        Log.d("HomeScreen", "HomeScreen: $playgrounds")
    }

    LaunchedEffect(localUser) {
        getHomeScreenData()
        getUserData()
//        Log.d("HomeScreen", "localUser recomposed")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        UserProfileHeader(
            userData = localUser,
            profileImage = uiState.profileImage,
            onClickUserImage = onClickUserImage,
            onClickBell = onClickBell
        )

        Spacer(modifier = Modifier.height(10.dp))

        HomeSearchBar(
            onSearchBarClicked = onSearchBarClicked
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(modifier = Modifier.fillMaxSize()) {
            HomeContent(
                playgroundsData = playgroundsData.sortedBy { it.distance },
                homeUiState = uiState,
                userId = localUser.userID ?: "",
                onAdClicked = onAdClicked,
                onClickViewAll = onClickViewAll,
                onClickPlaygroundCard = onClickPlaygroundCard,
                onFavouriteClick = { playgroundId -> onFavouriteClick(playgroundId) }
            )
            if (showLoading) {
                ThreeBounce(
                    color = MaterialTheme.colorScheme.primary,
                    size = DpSize(75.dp, 75.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun HomeContent(
    playgroundsData: List<Playground>,
    homeUiState: HomeUiState,
    context: Context = LocalContext.current,
    userId: String,
    onAdClicked: (String) -> Unit,
    onClickViewAll: () -> Unit,
    onClickPlaygroundCard: (Int, Boolean) -> Unit,
    onFavouriteClick: (Int) -> Unit
) {
    val playgroundListState = rememberLazyListState()

    //        -----------------Temporary-----------------           //
    val adsList = mutableListOf(
        AdsContent(
            imageSlider = painterResource(id = R.drawable.ads_ai),
            contentText = stringResource(id = R.string.ad_content_1),
        ),
        AdsContent(
            imageSlider = painterResource(id = R.drawable.playground),
            contentText = stringResource(id = R.string.ad_content_2),
        ),
        AdsContent(
            imageSlider = painterResource(id = R.drawable.playground_image),
            contentText = stringResource(id = R.string.ad_content_3),
        ),
        AdsContent(
            imageSlider = painterResource(id = R.drawable.playground),
            contentText = stringResource(id = R.string.ad_content_4),
        ),
    )

    val visiblePlaygrounds =
        if (homeUiState.viewAllSwitch) playgroundsData else playgroundsData.take(3)

    LaunchedEffect(key1 = homeUiState.viewAllSwitch) {
        if (homeUiState.viewAllSwitch && playgroundsData.size >= 4) {
            playgroundListState.animateScrollToItem(4)
        }
    }

    LazyColumn(
        state = playgroundListState,
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            AdsSlider(
                adsContent = adsList,
                userId = userId,
                onAdClicked = if (homeUiState.canUploadVideo) onAdClicked else { _ ->
                    Toast.makeText(
                        context,
                        context.getString(R.string.you_can_t_upload_video_now), Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }

        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(id = R.string.nearby_fields),
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = stringResource(id = R.string.view_all),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { onClickViewAll() }
                )
            }
        }

        items(visiblePlaygrounds) { playground ->
            PlaygroundCard(
                playground = playground,
                onFavouriteClick = { onFavouriteClick(playground.id) },
                onViewPlaygroundClick = {
                    onClickPlaygroundCard(
                        playground.id,
                        playground.isFavourite
                    )
                }
            )
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
fun HomeScreenPreview() {
    RentafieldTheme {
        val mockViewModel: MockHomeViewModel = hiltViewModel()
        HomeScreen(
            playgroundsState = mockViewModel.playgroundState,
            localUserState = MutableStateFlow(LocalUser()),
            homeUiState = mockViewModel.homeUiState,
            onClickUserImage = { },
            onClickBell = { },
            onSearchBarClicked = {},
            onClickViewAll = { mockViewModel.onClickViewAll() },
            onAdClicked = { },
            onClickPlaygroundCard = { _, _ -> },
            onFavouriteClick = {},
            getHomeScreenData = {},
            getUserData = {}
        )
    }
}
