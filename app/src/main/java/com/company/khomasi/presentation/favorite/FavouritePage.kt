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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.company.khomasi.R
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.FavouritePlaygroundResponse
import com.company.khomasi.domain.model.Playground
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.cards.PlaygroundCard
import com.company.khomasi.theme.KhomasiTheme
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavouritePage(
    uiState: StateFlow<FavouriteUiState>,
    favState: StateFlow<DataState<FavouritePlaygroundResponse>>,
    onFavouriteClick: (Int) -> Unit,
    onPlaygroundClick: (Int) -> Unit,
    getFavoritePlaygrounds: () -> Unit
) {
    LaunchedEffect(Unit) {
        getFavoritePlaygrounds()
    }
    val favUiState = uiState.collectAsState().value
    val response = favState.collectAsState().value
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar() },

        ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp),
            color = MaterialTheme.colorScheme.background,
        ) {
            if (favUiState.playgrounds.isNotEmpty())
                LazyColumn(
                    contentPadding = it,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    itemsIndexed(
                        items = favUiState.playgrounds,
                        itemContent = { index, playground ->

                            val deletedList = remember {
                                mutableStateListOf<Playground>(favUiState.playgrounds[index])
                            }
                            AnimatedVisibility(
                                visible = deletedList.contains(element = playground),
                                enter = expandVertically(),
                                exit = shrinkVertically(animationSpec = tween(durationMillis = 1000))
                            ) {

                                PlaygroundCard(
                                    playground = playground,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .animateItemPlacement(),
                                    onViewPlaygroundClick = { onPlaygroundClick(playground.id) },
                                    onFavouriteClick = {
                                        onFavouriteClick(playground.id)

                                        deletedList.add(playground)
                                    },
                                )
                            }
                        }
                    )

                } else {
                EmptyScreen()
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
            favState = mockViewModel.favState
        )
    }
}

