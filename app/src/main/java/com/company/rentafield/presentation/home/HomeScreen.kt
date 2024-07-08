package com.company.rentafield.presentation.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.company.rentafield.R
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.LocalUser
import com.company.rentafield.domain.model.playground.Playground
import com.company.rentafield.domain.model.playground.PlaygroundsResponse
import com.company.rentafield.presentation.components.AdsContent
import com.company.rentafield.presentation.components.AdsSlider
import com.company.rentafield.presentation.components.cards.PlaygroundCard
import com.company.rentafield.presentation.components.connectionStates.ThreeBounce
import com.company.rentafield.theme.KhomasiTheme
import com.company.rentafield.utils.convertToBitmap
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
//        Log.d("HomeScreen", "localUser recomposed")
    }
    LaunchedEffect(localUser) {
        getUserData()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        UserProfileSection(
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
    userId: String,
    onAdClicked: (String) -> Unit,
    onClickViewAll: () -> Unit,
    onClickPlaygroundCard: (Int, Boolean) -> Unit,
    onFavouriteClick: (Int) -> Unit
) {
    val context = LocalContext.current
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

    LazyColumn(
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


@Composable
fun UserProfileSection(
    userData: LocalUser,
    profileImage: String?,
    onClickUserImage: () -> Unit,
    onClickBell: () -> Unit
) {
    val imageRequest = remember(profileImage) {
        profileImage?.convertToBitmap() ?: ""
    }
    Row {
        SubcomposeAsyncImage(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface)
                .clickable { onClickUserImage() },
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(imageRequest)
                .crossfade(true).build(),
            loading = {
                CircularProgressIndicator()
            },
            error = {
                Image(
                    painter = painterResource(id = R.drawable.user_img),
                    contentDescription = null
                )
            },
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = "${stringResource(id = R.string.hello)} ${userData.firstName}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Text(
                text = stringResource(id = R.string.welcome_message),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer

            )
        }
        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = onClickBell) {
            Icon(
                painter = painterResource(id = R.drawable.bell),
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                contentDescription = null
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
