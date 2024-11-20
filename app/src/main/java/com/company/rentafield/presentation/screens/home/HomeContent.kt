package com.company.rentafield.presentation.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.company.rentafield.R
import com.company.rentafield.presentation.components.AdsSlider
import com.company.rentafield.presentation.components.cards.PlaygroundCard
import com.company.rentafield.presentation.components.connectstates.ThreeBounce
import com.company.rentafield.presentation.screens.home.components.HomeSearchBar
import com.company.rentafield.presentation.screens.home.components.UserProfileHeader
import com.company.rentafield.presentation.screens.home.model.adsList
import com.company.rentafield.presentation.theme.RentafieldTheme
import com.company.rentafield.utils.ThemePreviews

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    state: HomeReducer.State,
    sendEvent: (HomeReducer.Event) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        stickyHeader {
            Column(
                modifier = Modifier.background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            )
            {
                UserProfileHeader(
                    userFirstName = state.localUser.firstName ?: "",
                    profileImage = state.profileImage,
                    onClickUserImage = { sendEvent(HomeReducer.Event.ImageProfileClicked) },
                    onClickBell = { sendEvent(HomeReducer.Event.BellClicked) }
                )
                HomeSearchBar(
                    onSearchBarClicked = { sendEvent(HomeReducer.Event.SearchBarClicked) }
                )
            }
        }
        item {
            AdsSlider(
                adsContent = adsList,
                userId = state.localUser.userID ?: "",
                onAdClicked = { id ->
                    if (state.canUploadVideo) sendEvent(HomeReducer.Event.AdClicked(id))
                }
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val clickableModifier =
                    remember { Modifier.clickable { sendEvent(HomeReducer.Event.ViewAllClicked) } }
                Text(
                    text = stringResource(id = R.string.nearby_fields),
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = stringResource(id = R.string.view_all),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = clickableModifier
                )
            }
        }
        if (state.isLoading)
            item {
                ThreeBounce(
                    color = MaterialTheme.colorScheme.primary,
                    size = DpSize(75.dp, 75.dp),
                    modifier = Modifier
                        .fillParentMaxHeight(.5f)
                        .fillMaxWidth()
                )
            }
        else
            items(state.playgrounds) { playground ->
                PlaygroundCard(
                    playground = playground,
                    onFavouriteClick = { sendEvent(HomeReducer.Event.FavouriteClick(playground.id)) },
                    onViewPlaygroundClick = {
                        sendEvent(
                            HomeReducer.Event.PlaygroundClick(
                                playground.id,
                                playground.isFavourite
                            )
                        )
                    }
                )
            }
    }
}

@ThemePreviews
@Composable
fun HomeContentPreview() {
    RentafieldTheme {
        HomeContent(
            state = HomeReducer.initial().copy(isLoading = true),
            sendEvent = {}
        )
    }
}