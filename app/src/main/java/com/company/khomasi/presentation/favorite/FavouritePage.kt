package com.company.khomasi.presentation.favorite

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.company.khomasi.R
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.cards.PlaygroundCard
import com.company.khomasi.theme.KhomasiTheme
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavouritePage(
    uiState: StateFlow<FavouriteUiState>,
    onFavouriteClick: (Int) -> Unit,
    onPlaygroundClick: (Int) -> Unit,
    getFavoritePlaygrounds: () -> Unit
) {
    LaunchedEffect(Unit) {
        getFavoritePlaygrounds()
    }
    val favUiState = uiState.collectAsStateWithLifecycle().value
    val filteredPlaygrounds = favUiState.playgrounds.filter { playground ->
        !favUiState.deletedPlaygroundIds.contains(playground.id)
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp),
        topBar = { TopBar() },
        containerColor = MaterialTheme.colorScheme.background,

        )
    {
//            favUiState.deletedPlaygroundIds = remember {
//                mutableStateListOf<Playground>()
//            }
        LazyColumn(
                    contentPadding = it,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
            if (favUiState.playgrounds.isNotEmpty()) {
                itemsIndexed(
                    items = filteredPlaygrounds,
                    itemContent = { _, playground ->
                        AnimatedVisibility(
                            visible = !favUiState.deletedPlaygroundIds.contains(playground.id),
                            enter = expandVertically(),
                            exit = shrinkVertically(animationSpec = tween(durationMillis = 1000))
                        ) {
                            PlaygroundCard(
                                playground = playground,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                onViewPlaygroundClick = { onPlaygroundClick(playground.id) },
                                onFavouriteClick = {
                                    //deletedList.add(playground)
                                    onFavouriteClick(playground.id)
                                },
                            )
                        }
                    }
                )
            } else {
                item { EmptyScreen() }
            }

        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    Column {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.my_favorite_stadiums),
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.CenterStart)
                        .padding(start = 16.dp)
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.background)
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(), thickness = 1.dp
        )
    }
}

@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier,
    isDark: Boolean = isSystemInDarkTheme()
) {
    Column(
        modifier = modifier
            .padding(start = 24.dp, end = 24.dp, top = 153.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = if (isDark) painterResource(id = R.drawable.dark_fav_screen) else painterResource(
                id = R.drawable.light_empty_fav_screen
            ),
            contentDescription = "",
            alignment = Alignment.Center
        )
        Text(
            text = stringResource(R.string.no_favorite_fields),
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.height(108.dp))
        MyButton(
            text = R.string.browse_fields,
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun FavouritePagePreview() {
    KhomasiTheme {
        val mockViewModel: MockFavViewModel = viewModel()
        FavouritePage(
            uiState = mockViewModel.uiState,
            onFavouriteClick = { },
            onPlaygroundClick = {},
            getFavoritePlaygrounds = {},
        )
    }
}

