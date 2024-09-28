package com.company.rentafield.presentation.screens.home.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.company.rentafield.R
import com.company.rentafield.domain.model.playground.Playground
import com.company.rentafield.presentation.components.AdsSlider
import com.company.rentafield.presentation.components.cards.PlaygroundCard
import com.company.rentafield.presentation.screens.home.model.AdsContent
import com.company.rentafield.presentation.screens.home.model.adsList
import com.company.rentafield.presentation.screens.home.vm.HomeReducer
import com.company.rentafield.utils.ThemePreviews

@Composable
fun HomeContent(
    playgroundsData: List<Playground>,
    firstName: String,
    profileImage: String,
    adsList: List<AdsContent>,
    canUploadVideo: Boolean,
    userId: String,
    sendEvent: (HomeReducer.Event) -> Unit,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        UserProfileHeader(
            userFirstName = firstName,
            profileImage = profileImage,
            onClickUserImage = { sendEvent(HomeReducer.Event.ImageProfileClicked) },
            onClickBell = { sendEvent(HomeReducer.Event.BellClicked) }
        )

        HomeSearchBar(onSearchBarClicked = { sendEvent(HomeReducer.Event.SearchBarClicked) })
        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                AdsSlider(
                    adsContent = adsList,
                    userId = userId,
                    onAdClicked = { id ->
                        if (canUploadVideo) sendEvent(HomeReducer.Event.AdClicked(id))
                        else Toast.makeText(
                            context,
                            context.getString(R.string.you_can_t_upload_video_now),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxSize(),
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

            items(playgroundsData) { playground ->
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
}

@ThemePreviews
@Composable
fun HomeContentPreview() {
    HomeContent(
        firstName = "",
        profileImage = "",
        playgroundsData = emptyList(),
        adsList = adsList,
        canUploadVideo = true,
        userId = "1",
        sendEvent = {},
        modifier = Modifier.fillMaxSize()
    )
}