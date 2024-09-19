package com.company.rentafield.presentation.screens.home.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import com.company.rentafield.presentation.screens.home.constants.adsList
import com.company.rentafield.presentation.screens.home.model.AdsContent
import com.company.rentafield.presentation.screens.home.model.HomeUiState
import com.company.rentafield.utils.ThemePreviews

@Composable
fun HomeContent(
    playgroundsData: List<Playground>,
    adsList: List<AdsContent>,
    homeUiState: HomeUiState,
    userId: String,
    onAdClicked: (String) -> Unit,
    onClickViewAll: () -> Unit,
    onClickPlaygroundCard: (Int, Boolean) -> Unit,
    onFavouriteClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
) {
    LazyColumn(
        modifier = modifier,
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
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val clickableModifier =
                    remember { Modifier.clickable { onClickViewAll() } }
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
                onFavouriteClick = { onFavouriteClick(playground.id) },
                onViewPlaygroundClick = {
                    onClickPlaygroundCard(playground.id, playground.isFavourite)
                }
            )
        }
    }
}

@ThemePreviews
@Composable
fun HomeContentPreview() {
    HomeContent(
        playgroundsData = emptyList(),
        adsList = adsList,
        homeUiState = HomeUiState(),
        userId = "1",
        onAdClicked = { },
        onClickViewAll = { },
        onClickPlaygroundCard = { _, _ -> },
        onFavouriteClick = { },
        modifier = Modifier.fillMaxSize()
    )
}