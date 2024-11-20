package com.company.rentafield.presentation.screens.playground.components

import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.company.rentafield.R
import com.company.rentafield.domain.DataState
import com.company.rentafield.presentation.components.connectstates.ThreeBounce
import com.company.rentafield.presentation.components.iconbuttons.RoundedFavoriteIcon
import com.company.rentafield.presentation.screens.playground.model.PlaygroundInfoUiState
import com.company.rentafield.presentation.theme.RentafieldTheme
import com.company.rentafield.utils.convertToBitmap
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageSlider(
    playgroundStateFlow: StateFlow<DataState<com.company.rentafield.data.models.playground.PlaygroundScreenResponse>>,
    playgroundInfoUiState: StateFlow<PlaygroundInfoUiState>,
    onClickBack: () -> Unit,
    onClickShare: () -> Unit,
//    onClickFav: (Boolean) -> Unit,
    context: Context = LocalContext.current
) {
    val uiState by playgroundInfoUiState.collectAsStateWithLifecycle()

    val playgroundStateResponse by playgroundStateFlow.collectAsStateWithLifecycle()

    var playgroundData by rememberSaveable {
        mutableStateOf<List<com.company.rentafield.data.models.playground.PlaygroundPicture>?>(
            null
        )
    }

    LaunchedEffect(playgroundStateResponse) {
        if (playgroundStateResponse is DataState.Success) {
            playgroundData = (playgroundStateResponse as DataState.Success).data.playgroundPictures
        }
    }
    val pagerState = rememberPagerState(initialPage = 0)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(266.dp)
    ) {
        HorizontalPager(
            count = playgroundData?.size ?: 0,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(246.dp)
        ) { page ->
            SubcomposeAsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(context = context)
                    .data(playgroundData?.get(page)?.picture?.convertToBitmap()).crossfade(true)
                    .build(),
                loading = {
                    ThreeBounce(
                        color = MaterialTheme.colorScheme.primary,
                        size = DpSize(75.dp, 75.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                    )
                },
                error = {
                    Image(
                        painter = painterResource(id = R.drawable.playground),
                        contentDescription = null
                    )
                },
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
            )
        }


        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, start = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ButtonWithIcon(R.drawable.back) { onClickBack() }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.weight(1f))
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    inactiveColor = Color.White.copy(0.3f),
                    activeColor = Color.White,
                    spacing = 4.dp,
                    indicatorWidth = 15.dp,
                    indicatorHeight = 15.dp
                )
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    ButtonWithIcon(iconId = R.drawable.sharenetwork) { onClickShare() }
                    Spacer(modifier = Modifier.padding(4.dp))
                    RoundedFavoriteIcon(
                        onFavoriteClick = {
//                            onClickFav(uiState.isFavourite)
                        },
                        isFavorite = uiState.isFavourite,
                    )
                }
            }
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
fun ImageSliderPreview() {
    RentafieldTheme {
        ImageSlider(
            playgroundStateFlow = MutableStateFlow(
                DataState.Success(
                    com.company.rentafield.data.models.playground.PlaygroundScreenResponse(
                        playgroundPictures = listOf(),
                        playground = com.company.rentafield.data.models.playground.PlaygroundX(
                            id = 2,
                            name = "Adventure Island",
                            description = "A thrilling playground with exciting obstacle courses and climbing structures.",
                            advantages = "Challenging activities, promotes physical fitness, great for older kids.",
                            address = "456 Elm Street, Townsville",
                            type = 2,
                            rating = 4.5,
                            country = "Canada",
                            city = "Townsville",
                            latitude = 43.6532,
                            longitude = -79.3832,
                            holidays = "Thanksgiving Day, Boxing Day",
                            openingHours = "9:00 AM - 7:00 PM",
                            feesForHour = 25,
                            cancellationFees = 10,
                            isBookable = true,
                            rules = "Wear appropriate footwear, adult supervision required for children under 10."
                        )
                    )
                )
            ),
            playgroundInfoUiState = MutableStateFlow(PlaygroundInfoUiState()),
            onClickBack = {},
//            onClickFav = {},
            onClickShare = {}
        )
    }
}