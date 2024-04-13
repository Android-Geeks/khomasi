package com.company.khomasi.presentation.playground


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.company.khomasi.domain.model.PlaygroundScreenResponse
import android.content.Context
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.company.khomasi.R
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.PlaygroundPicture
import com.company.khomasi.presentation.components.AuthSheet
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.MyOutlinedButton
import com.company.khomasi.presentation.components.connectionStates.ThreeBounce
import com.company.khomasi.presentation.components.iconButtons.FavoriteIcon
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.theme.darkIcon
import com.company.khomasi.theme.darkIconMask
import com.company.khomasi.theme.darkText
import com.company.khomasi.theme.lightIcon
import com.company.khomasi.theme.lightIconMask
import com.company.khomasi.theme.lightText
import com.company.khomasi.utils.convertToBitmap
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.StateFlow

/*
/////////////////////////////////////////////////////////////////////////////////////
      Still want to solve the issue of favourite icon
      Add the logic of the PlaygroundId when navigate from home
/////////////////////////////////////////////////////////////////////////////////////

*/
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun PlaygroundScreen(
    playgroundStateFlow: StateFlow<DataState<PlaygroundScreenResponse>>,
    playgroundUiState: StateFlow<PlaygroundUiState>,
    context: Context = LocalContext.current,
    onViewRatingClicked: () -> Unit,
    getPlaygroundDetails: () -> Unit,
    onClickBack: () -> Unit,
    onClickShare: () -> Unit,
    onClickFav: () -> Unit,
    onBookNowClicked: () -> Unit,
    onClickDisplayOnMap: () -> Unit
) {
    var showLoading by remember { mutableStateOf(false) }
    val uiState = playgroundUiState.collectAsState().value
    val playgroundState = playgroundStateFlow.collectAsState().value

    LaunchedEffect(Unit) {
        getPlaygroundDetails()
    }

    LaunchedEffect(playgroundState) {
        showLoading = playgroundState is DataState.Loading
        Log.d("PlaygroundScreen", "PlaygroundScreen: $playgroundState")
    }

    if (playgroundState is DataState.Success) {
        val playgroundData = playgroundState.data
        AuthSheet(sheetModifier = Modifier.fillMaxWidth(), screenContent = {
            PlaygroundScreenContent(playgroundData = playgroundData,
                uiState = uiState,
                onViewRatingClicked = onViewRatingClicked,
                onClickBack = onClickBack,
                onClickShare = onClickShare,
                onClickFav = { onClickFav() },
                onClickDisplayOnMap = { onClickDisplayOnMap() })
        }) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .height(116.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Text(
                    text = context.getString(
                        R.string.fees_per_hour, playgroundData.playground.feesForHour
                    )
                )

                MyButton(
                    text = R.string.book_now,
                    onClick = { onBookNowClicked() },
                    modifier = Modifier.fillMaxWidth()
                )

            }
        }
    }
    if (showLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
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

@Composable
fun PlaygroundScreenContent(
    playgroundData: PlaygroundScreenResponse,
    uiState: PlaygroundUiState,
    onViewRatingClicked: () -> Unit,
    onClickBack: () -> Unit,
    onClickShare: () -> Unit,
    onClickFav: () -> Unit,
    onClickDisplayOnMap: () -> Unit
) {

    LazyColumn(
        Modifier.fillMaxSize()
    ) {

        item {
            ImageSlider(imageList = playgroundData.playgroundPictures,
                isFav = uiState.isFavourite,
                onClickBack = { onClickBack() },
                onClickShare = { onClickShare() },
                onClickFav = { onClickFav() })
        }

        item {
            PlaygroundDefinition(name = "playgroundData.playground.name",
                openingTime = playgroundData.playground.openingHours,
                address = "playgroundData.playground.address",
                onClickDisplayOnMap = { onClickDisplayOnMap() })
        }

        item { LineSpacer() }

        item {
            PlaygroundRates(
                rateNum = "23",
                rate = playgroundData.playground.rating.toString(),
                onViewRatingClicked = onViewRatingClicked
            )
        }

        item { LineSpacer() }

        item { PlaygroundSize(size = stringResource(R.string.field_size_5x5)) }

        item { LineSpacer() }

        item { PlaygroundDescription(description = playgroundData.playground.description) }

        item { LineSpacer() }


        item { PlaygroundFeatures(featureList = playgroundData.playground.advantages.split(",")) }

        item { LineSpacer() }


        item {
            PlaygroundRules(rulesList = "playgroundData.playground.rules".split(","))
        }


        item { Spacer(modifier = Modifier.height(146.dp)) }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageSlider(
    imageList: List<PlaygroundPicture>,
    isFav: Boolean,
    onClickBack: () -> Unit,
    onClickShare: () -> Unit,
    onClickFav: () -> Unit
) {
    val pagerState = rememberPagerState(initialPage = 0)
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(246.dp)
    ) {
        Log.d("ImageSlider", "Re: ${imageList.size}")
        HorizontalPager(
            count = imageList.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(226.dp)
        ) { page ->
            AsyncImage(
                model = ImageRequest.Builder(context = context)
                    .data(imageList[page].picture.convertToBitmap()).crossfade(true).build(),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize(),
                placeholder = painterResource(R.drawable.playground)

            )

            Column(
                Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom
            ) {
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    inactiveColor = Color.White.copy(alpha = 0.3f),
                    activeColor = Color.White,
                    indicatorWidth = 15.dp,
                    indicatorHeight = 15.dp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 8.dp)
                )

            }
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
                    .fillMaxWidth()
                    .padding(end = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                ButtonWithIcon(iconId = R.drawable.sharenetwork) { onClickShare() }
                Spacer(modifier = Modifier.width(8.dp))
                Card(
                    shape = CircleShape, modifier = Modifier.size(44.dp)
                ) {
                    Column(
                        Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        FavoriteIcon(
                            onFavoriteClick = { onClickFav() },
                            isFavorite = isFav,
                            modifier = Modifier.size(24.dp),
                            inactiveColor = Color.Black
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ButtonWithIcon(
    iconId: Int, onClick: () -> Unit
) {
    Card(shape = CircleShape, modifier = Modifier
        .size(44.dp)
        .clickable { onClick() }) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null,
                tint = if (isSystemInDarkTheme()) darkIcon else lightIcon,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun PlaygroundFeatures(
    featureList: List<String>
) {
    val myHeight = if (featureList.size > 3) 220.dp else 110.dp
    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(myHeight)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.field_features),
                style = MaterialTheme.typography.titleLarge
            )
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 100.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(featureList.take(6)) { feature ->
                    Card(
                        modifier = Modifier.height(50.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                        shape = MaterialTheme.shapes.medium,
                        colors = CardDefaults.cardColors(Color.Transparent)
                    ) {
                        Text(
                            text = feature,
                            style = MaterialTheme.typography.displayMedium,
                            color = MaterialTheme.colorScheme.tertiary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 3.dp, horizontal = 10.dp)
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun PlaygroundDefinition(
    name: String, openingTime: String, address: String, onClickDisplayOnMap: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(94.dp)
        ) {
            Row(
                Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PlaygroundStatus(
                    status = "مفتوح الأن"
                )
            }
        }
        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        ) {
            Text(
                text = name, style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            IconWithText(text = openingTime, iconId = R.drawable.clock)

            IconWithText(text = address, iconId = R.drawable.mappin)

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(32.dp), horizontalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 93.dp, end = 109.dp)
                        .clickable { onClickDisplayOnMap() },
                    colors = CardDefaults.cardColors(if (isSystemInDarkTheme()) darkText else lightText)
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painterResource(id = R.drawable.logos_google_maps),
                            contentDescription = null,
                            modifier = Modifier.size(width = 11.dp, height = 16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = stringResource(R.string.Show_on_map),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.background,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun PlaygroundStatus(
    status: String
) {
    Box {
        Card(
            modifier = Modifier.size(width = 99.dp, height = 40.dp),
            colors = CardDefaults.cardColors(if (isSystemInDarkTheme()) darkIconMask else lightIconMask),
            shape = RectangleShape
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = status,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displayMedium,
                )
            }
        }
    }
}

@Composable
fun PlaygroundSize(
    size: String
) {
    Box {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = stringResource(id = R.string.field_size),
                style = MaterialTheme.typography.titleLarge
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = MaterialTheme.shapes.medium
                    ), colors = CardDefaults.cardColors(Color.Transparent)
            ) {
                Row(
                    Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = size,
                        style = MaterialTheme.typography.displayMedium,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }

            }
        }
    }
}

@Composable
fun PlaygroundRules(
    rulesList: List<String>
) {
    val myHeight = (30.dp * rulesList.size).coerceAtLeast(40.dp)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(myHeight)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.field_instructions),
            style = MaterialTheme.typography.titleLarge,
            color = if (isSystemInDarkTheme()) darkText else lightText
        )

        for (i in rulesList.indices.take(6)) {
            Text(
                text = " ${i + 1}. ${rulesList[i]}",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.tertiary,
            )
        }
    }
}

@Composable
fun PlaygroundRates(
    rateNum: String, rate: String, onViewRatingClicked: () -> Unit
) {
    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row {
                Text(
                    text = stringResource(id = R.string.ratings),
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "($rateNum ${stringResource(id = R.string.rate)})",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    Text(
                        text = rate.take(3), style = MaterialTheme.typography.bodyLarge
                    )
                    Icon(
                        painter = painterResource(R.drawable.unfilled_star),
                        contentDescription = null
                    )
                }
            }

            MyOutlinedButton(
                text = R.string.view_ratings,
                onClick = { onViewRatingClicked() },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun PlaygroundDescription(
    description: String
) {
    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.field_description),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = description,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

@Composable
fun IconWithText(
    @DrawableRes iconId: Int,
    text: String,
) {
    Row(
        Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(iconId), contentDescription = null
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Composable
fun LineSpacer() {
    Spacer(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .height(0.5.dp)
            .border(width = 0.5.dp, color = MaterialTheme.colorScheme.outline)
    )
}

@Preview(locale = "ar", showSystemUi = true)
@Composable
fun PlaygroundScreenPreview() {
    val mockViewModel: MockPlaygroundViewModel = hiltViewModel()

    KhomasiTheme {
        PlaygroundScreen(playgroundStateFlow = mockViewModel.playgroundState,
            playgroundUiState = mockViewModel.uiState,
            onViewRatingClicked = {},
            onClickShare = {},
            onClickBack = {},
            onClickFav = {},
            onBookNowClicked = {},
            onClickDisplayOnMap = {},
            getPlaygroundDetails = {}
        )

    }

}


/*

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun PlaygroundScreen() {
    val playgroundViewModel: PlaygroundViewModel = hiltViewModel()
    val state = playgroundViewModel.playgroundState.collectAsState().value
    LaunchedEffect(key1 = playgroundViewModel.playgroundState.value) {
        Log.d("FavoriteScreen", "FavoriteScreen: ${playgroundViewModel.playgroundState.value}")
    }
    if (state is DataState.Success) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Text(text = state.data.playground.holidays)
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(state.data.playgroundPictures[0].picture.convertToBitmap())
                    .crossfade(true).build(), contentDescription = null
            )
        }
    }

}
*/
