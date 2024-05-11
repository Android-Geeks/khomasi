package com.company.khomasi.presentation.playground.components

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.company.khomasi.R
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.PlaygroundPicture
import com.company.khomasi.domain.model.PlaygroundScreenResponse
import com.company.khomasi.presentation.components.connectionStates.ThreeBounce
import com.company.khomasi.presentation.components.iconButtons.RoundedFavoriteIcon
import com.company.khomasi.presentation.playground.ButtonWithIcon
import com.company.khomasi.presentation.playground.PlaygroundUiState
import com.company.khomasi.utils.convertToBitmap
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageSlider(
    playgroundStateFlow: StateFlow<DataState<PlaygroundScreenResponse>>,
    playgroundState: StateFlow<PlaygroundUiState>,
    onClickBack: () -> Unit,
    onClickShare: () -> Unit,
    onClickFav: (String, Boolean) -> Unit,
) {
    val uiState by playgroundState.collectAsStateWithLifecycle()

    val playgroundState1 by playgroundStateFlow.collectAsStateWithLifecycle()
    val favState = uiState.favPlayground

    var playgroundData by remember { mutableStateOf<List<PlaygroundPicture>?>(null) }

    LaunchedEffect(playgroundState1) {
        if (playgroundState1 is DataState.Success) {
            playgroundData = (playgroundState1 as DataState.Success).data.playgroundPictures
        }
    }
    val pagerState = rememberPagerState(initialPage = 0)
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(246.dp)
    ) {
        HorizontalPager(
            count = playgroundData?.size ?: 0,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(226.dp)
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
                        painter = painterResource(id = R.drawable.user_img),
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
                    .padding(top = 12.dp, start = 16.dp),
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
                            onClickFav(
                                favState.id.toString(),
                                favState.isFavourite
                            )
                        },
                        isFavorite = uiState.isFavourite,
                    )
                }
            }
        }
    }
}