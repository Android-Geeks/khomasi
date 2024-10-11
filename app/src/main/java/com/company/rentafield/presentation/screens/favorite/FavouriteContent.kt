package com.company.rentafield.presentation.screens.favorite

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.company.rentafield.R
import com.company.rentafield.presentation.components.SubScreenTopBar
import com.company.rentafield.presentation.components.cards.PlaygroundCard
import com.company.rentafield.presentation.components.connectionStates.ThreeBounce

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavouriteContent(
    uiState: FavouriteReducer.State,
    sendEvent: (FavouriteReducer.Event) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            SubScreenTopBar(
                title = stringResource(R.string.favourites),
                onBackClick = { sendEvent(FavouriteReducer.Event.BackClicked) },
            )
        }
    ) { paddingValues ->

        if (uiState.isLoading) ThreeBounce(modifier = Modifier.fillMaxSize())
        else if (uiState.favouritePlaygrounds.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = paddingValues,
            ) {
                items(uiState.favouritePlaygrounds, key = { it.id }) { playground ->
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
                            sendEvent(
                                FavouriteReducer.Event.PlaygroundClick(
                                    playground.id,
                                    playground.isFavourite
                                )
                            )
                        },
                        onFavouriteClick = { sendEvent(FavouriteReducer.Event.FavouriteClick(playground.id)) },
                    )
                }
            }
        } else EmptyScreen()
    }
}