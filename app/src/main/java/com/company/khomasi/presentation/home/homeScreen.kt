package com.company.khomasi.presentation.home

import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.company.khomasi.R
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.LocalUser
import com.company.khomasi.domain.model.PlaygroundsResponse
import com.company.khomasi.presentation.components.AdsContent
import com.company.khomasi.presentation.components.AdsSlider
import com.company.khomasi.presentation.components.cards.PlaygroundCard
import com.company.khomasi.presentation.components.connectionStates.ThreeBounce
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.utils.convertToBitmap
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
    onAdClicked: () -> Unit,
    onClickPlaygroundCard: (Int, String, Int) -> Unit,
    onFavouriteClick: (Int) -> Unit,
    getPlaygrounds: () -> Unit
) {
    val playgrounds = playgroundsState.collectAsState().value
    val uiState = homeUiState.collectAsState().value
    val localUser = localUserState.collectAsState().value

    var showLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        getPlaygrounds()
    }

    LaunchedEffect(playgrounds) {
        showLoading = playgrounds is DataState.Loading
    }



    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 12.dp)
        ) {

            UserProfileSection(
                userData = localUser,
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
                    playgroundState = playgrounds,
                    homeUiState = uiState,
                    onAdClicked = { onAdClicked() },
                    onClickViewAll = { onClickViewAll() },
                    onClickPlaygroundCard = { playgroundId, playgroundName, playgroundPrice ->
                        onClickPlaygroundCard(playgroundId, playgroundName, playgroundPrice)
                    },
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
}

@Composable
fun HomeContent(
    playgroundState: DataState<PlaygroundsResponse>,
    homeUiState: HomeUiState,
    onAdClicked: () -> Unit,
    onClickViewAll: () -> Unit,
    onClickPlaygroundCard: (Int, String, Int) -> Unit,
    onFavouriteClick: (Int) -> Unit
) {
    //        -----------------Temporary-----------------           //
    val adsList = listOf(
        AdsContent(
            imageSlider = painterResource(id = R.drawable.playground),
            contentText = " احجز اى ملعب بخصم 10 %",
        ),
        AdsContent(
            imageSlider = painterResource(id = R.drawable.playground_image),
            contentText = "احصل على خصم 20% عند دعوة 5 أصدقاء",
        ),
        AdsContent(
            imageSlider = painterResource(id = R.drawable.playground),
            contentText = " احجز اى ملعب صباحًا بخصم 30 %",
        ),
    )
    if (playgroundState is DataState.Success) {

        val playgrounds = playgroundState.data.playgrounds/*.sortedBy { it.id }*/
        val visiblePlaygrounds =
            if (homeUiState.viewAllSwitch) playgrounds else playgrounds.take(3)

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

//      ------------ Temporary until the booking page is completed   -----//
            /*            if(true){
                            item {
                                RatingCard(
                                    buttonText = R.string.rating,
                                    mainText = "كانت مباراه حماسيه",
                                    subText = "اليوم الساعه 9:00 م",
                                    timeIcon = R.drawable.clock
                                )
                            }
                        }*/
//  -------------------------------------------------------------------//

            item { AdsSlider(adsContent = adsList, onAdClicked = { onAdClicked() }) }

            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(id = R.string.nearby_fields),
                        style = MaterialTheme.typography.displayMedium
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
                    onFavouriteClick = { onFavouriteClick(playground.id) },          // WILL BE IMPLEMENTED LATER
                    onViewPlaygroundClick = {
                        onClickPlaygroundCard(
                            playground.id,
                            playground.name,
                            playground.feesForHour
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun UserProfileSection(
    userData: LocalUser,
    onClickUserImage: () -> Unit,
    onClickBell: () -> Unit
) {
    Row {
        SubcomposeAsyncImage(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .clickable { onClickUserImage() },
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(
                    if (userData.profilePicture != null)
                        userData.profilePicture.convertToBitmap()
                    else R.drawable.user_img
                )
                .crossfade(true).build(),
            loading = {
                CircularProgressIndicator()
            },
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = "${stringResource(id = R.string.hello)} ${
                    userData.firstName
                }",
                style = MaterialTheme.typography.bodyMedium,

                )

            Text(
                text = stringResource(id = R.string.welcome_message),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = { onClickBell() }) {
            Icon(
                painter = painterResource(id = R.drawable.bell), contentDescription = null
            )
        }
    }
}

@Composable
fun HomeSearchBar(onSearchBarClicked: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(Color.Transparent),
        modifier = Modifier
            .clickable { onSearchBarClicked() }
            .fillMaxWidth()
            .height(38.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = MaterialTheme.shapes.medium
            )) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = stringResource(id = R.string.search_for_playgrounds),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.tertiary
            )

            Spacer(modifier = Modifier.weight(1f))


            Icon(
                painter = painterResource(id = R.drawable.magnifyingglass),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.size(19.dp)
            )

        }
    }
}

@Preview(showSystemUi = true, locale = "ar")
@Composable
fun HomeScreenPreview() {
    KhomasiTheme {
        val mockViewModel: MockHomeViewModel = hiltViewModel()
        HomeScreen(
            playgroundsState = mockViewModel.playgroundState,
            homeUiState = mockViewModel.homeUiState,
            onClickUserImage = { },
            onClickBell = { },
            onSearchBarClicked = {},
            onClickViewAll = { mockViewModel.onClickViewAll() },
            onAdClicked = { },
            onClickPlaygroundCard = { playgroundId, playgroundName, playgroundPrice ->
                mockViewModel.onClickPlaygroundCard(playgroundId, playgroundName, playgroundPrice)
            },
            onFavouriteClick = {},
            getPlaygrounds = {},
            localUserState = MutableStateFlow(LocalUser())
        )
    }
}
