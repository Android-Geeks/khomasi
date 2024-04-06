package com.company.khomasi.presentation.favorite

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.company.khomasi.domain.model.PlaygroundsResponse
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.cards.PlaygroundCard
import com.company.khomasi.theme.KhomasiTheme
import kotlinx.coroutines.flow.StateFlow

@Composable
fun FavouritePage(
    favouritePlayground: StateFlow<DataState<FavouritePlaygroundResponse>>,
) {

    val favouritePlaygroundState by favouritePlayground.collectAsState()
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
                        LazyColumn(
                            contentPadding = it,
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                           val response = (favouritePlaygroundState as DataState.Success).data.playgrounds
                            if (response.isNotEmpty()) {
                                items(response) { playground ->
                                PlaygroundCard(
                                    playground = playground,
                                    modifier = Modifier.fillMaxWidth(),
                                    onViewPlaygroundClick = {},
                                )
                            }

                            } else {
                                item {
                                    EmptyScreen()
                                }
                            }
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
) {
    Column(
        modifier = modifier
            .padding(start = 24.dp, end = 24.dp, top = 153.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.frame),
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
//        val mockData = List(10) {
//
//            Playground(
//                id = 1,
//                name = "Playground Name",
//                address = "Address",
//                rating = 4.5,
//                feesForHour = 100,
//                isBookable = true,
//                distance = 5.0,
//                playgroundPicture = null,
//                isFavourite = true
//            )
//        }
       // val mockFavouritePlaygroundResponse = FavouritePlaygroundResponse(mockData, 10)
        //val mockDataState = DataState.Success(mockFavouritePlaygroundResponse)
        FavouritePage(
            //getUserFavoritePlaygrounds = mockViewModel::getUserFavoritePlaygrounds,
            //uiState = mockViewModel.uiState,
             favouritePlayground = mockViewModel.favouritePlaygroundsState,
        )
    }
}

