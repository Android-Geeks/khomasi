package com.company.khomasi.presentation.venues


import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.khomasi.R
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.FilteredPlaygroundResponse
import com.company.khomasi.domain.model.LocalUser
import com.company.khomasi.domain.model.Playground
import com.company.khomasi.presentation.components.SubScreenTopBar
import com.company.khomasi.presentation.components.cards.PlaygroundCard
import com.company.khomasi.presentation.components.connectionStates.Loading
import com.company.khomasi.theme.KhomasiTheme
import kotlinx.coroutines.flow.StateFlow

@Composable
fun BrowseResults(
    browseUiState: StateFlow<BrowseUiState>,
    filteredPlayground: StateFlow<DataState<FilteredPlaygroundResponse>>,
    localUser: StateFlow<LocalUser>,
    getFilteredPlaygrounds: () -> Unit,
    onFilterClick: () -> Unit,
    onBackClick: () -> Unit,
    isDark: Boolean = isSystemInDarkTheme(),
) {
    val user = localUser.collectAsStateWithLifecycle()
    val uiState by browseUiState.collectAsStateWithLifecycle()
    val filteredPlaygrounds by filteredPlayground.collectAsStateWithLifecycle()
    var showLoading by remember { mutableStateOf(false) }
    val filteredPlaygroundsList = remember { mutableStateOf<List<Playground>?>(null) }

    LaunchedEffect(filteredPlaygrounds) {
        getFilteredPlaygrounds()
        Log.d("BrowseResults", "FilteredPlaygrounds: $filteredPlaygrounds")
    }

    LaunchedEffect(filteredPlaygrounds) {
        when (filteredPlaygrounds) {
            is DataState.Success -> {
                showLoading = false
                filteredPlaygroundsList.value =
                    (filteredPlaygrounds as DataState.Success<FilteredPlaygroundResponse>).data.filteredPlaygrounds
            }

            is DataState.Error -> {
                showLoading = false

            }

            is DataState.Loading -> {
                showLoading = true
            }

            is DataState.Empty -> {}
        }
    }

    if (showLoading) {
        Loading()
    }

    Scaffold(
        topBar = {
            SubScreenTopBar(
                title = R.string.browse_fields,
                onBackClick = {},
                navigationIcon = {},
                actions = {
                    IconButton(
                        onClick = onFilterClick
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.sliders),
                            contentDescription = stringResource(id = R.string.filter_results)

                        )
                    }
                }
            )
        }
    ) {

        if (filteredPlaygroundsList.value != null) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                items(filteredPlaygroundsList.value!!) { playground ->
                    PlaygroundCard(
                        playground = playground,
                        onFavouriteClick = {},
                        onViewPlaygroundClick = {},
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

            }
        }
    }
}

@Preview
@Composable
fun BrowsePreview() {
    KhomasiTheme {
        val mockBrowseViewModel = MockBrowseViewModel()
        BrowseResults(
            browseUiState = mockBrowseViewModel.uiState,
            filteredPlayground = mockBrowseViewModel.filteredPlaygrounds,
            localUser = mockBrowseViewModel.localUser,
            getFilteredPlaygrounds = { mockBrowseViewModel.getPlaygrounds() },
            onFilterClick = { },
            onBackClick = { },
        )
    }
}