package com.company.rentafield.presentation.screens.favorite

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.company.rentafield.R
import com.company.rentafield.presentation.components.SubScreenTopBar
import com.company.rentafield.presentation.components.cards.PlaygroundCard
import com.company.rentafield.theme.RentafieldTheme
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavouriteScreen(
    uiState: StateFlow<FavouriteUiState>,
    onFavouriteClick: (Int) -> Unit,
    onPlaygroundClick: (Int, Boolean) -> Unit,
    getFavoritePlaygrounds: () -> Unit,
) {
    LaunchedEffect(Unit) {
        getFavoritePlaygrounds()
    }
    val favUiState = uiState.collectAsStateWithLifecycle().value

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            SubScreenTopBar(
                title = stringResource(R.string.favourites),
                onBackClick = {},
                navigationIcon = {},
            )
        }
    ) { paddingValues ->
        if (favUiState.playgrounds.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = paddingValues,
            ) {
                items(favUiState.playgrounds, key = { it.id }) { playground ->
                    PlaygroundCard(
                        playground = playground,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp, start = 16.dp, top = 16.dp)
                            .animateItemPlacement(
                                animationSpec = TweenSpec(
                                    durationMillis = 300,
                                    easing = FastOutLinearInEasing
                                )
                            ),
                        onViewPlaygroundClick = {
                            onPlaygroundClick(
                                playground.id,
                                playground.isFavourite
                            )
                        },
                        onFavouriteClick = {
                            onFavouriteClick(playground.id)
                        },
                    )
                }
            }
        } else {
            EmptyScreen()
        }
    }
}

@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier,
    isDark: Boolean = isSystemInDarkTheme()
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(
                if (isDark)
                    R.drawable.dark_fav_screen
                else
                    R.drawable.light_empty_fav_screen
            ),
            contentDescription = null,
            alignment = Alignment.Center
        )
        Text(
            text = stringResource(R.string.no_favorite_fields),
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleSmall
        )
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
fun FavouritePagePreview() {
    RentafieldTheme {
        val mockViewModel: MockFavViewModel = viewModel()
        FavouriteScreen(
            uiState = mockViewModel.uiState,
            onFavouriteClick = { },
            onPlaygroundClick = { _, _ -> },
            getFavoritePlaygrounds = {},
        )
    }
}

