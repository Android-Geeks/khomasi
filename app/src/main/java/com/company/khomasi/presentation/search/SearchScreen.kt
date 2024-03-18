package com.company.khomasi.presentation.search

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.domain.model.Playground
import com.company.khomasi.presentation.components.MyAlertDialog
import com.company.khomasi.presentation.navigation.components.MyModalBottomSheet
import com.company.khomasi.presentation.search.components.EmptySearch
import com.company.khomasi.presentation.search.components.MyTopAppBar
import com.company.khomasi.presentation.search.components.SearchHistoryItem
import com.company.khomasi.presentation.search.components.SearchResultsItem
import com.company.khomasi.presentation.search.components.SearchTopAppBar
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.theme.darkText
import com.company.khomasi.theme.lightText
import kotlinx.coroutines.flow.StateFlow

@Composable
fun SearchScreen(
    searchUiState: StateFlow<SearchUiState>,
    playgroundsState: StateFlow<List<Playground>>,
    searchQuery: StateFlow<String>,
    onBackClick: () -> Unit,
    onQueryChange: (String) -> Unit,
    onSearchQuerySubmitted: (String) -> Unit,
    onClearHistory: () -> Unit,
    navigateToPlaygroundDetails: (String) -> Unit,
    onSearchFilterChanged: (SearchFilter) -> Unit,
    isDark: Boolean = isSystemInDarkTheme(),
    onBackPage: () -> Unit,
    onNextPage: () -> Unit,
) {
    val uiState by searchUiState.collectAsState()
    val playgrounds by playgroundsState.collectAsState()
    val query by searchQuery.collectAsState()
    var showDeleteSearchHistoryDialog by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current

    BackHandler {
        if (uiState.page == 2) {
            onBackPage()
        } else {
            onBackClick()
        }
    }

    if (uiState.page == 1) {
        if (showDeleteSearchHistoryDialog) {
            MyAlertDialog(
                title = R.string.clear_history_,
                text = R.string.confirm_clear_history,
                onDismissRequest = { showDeleteSearchHistoryDialog = false },
                onConfirmButtonClick = {
                    onClearHistory()
                    showDeleteSearchHistoryDialog = false
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteSearchHistoryDialog = false }) {
                        Text(
                            text = stringResource(id = R.string.cancel),
                            style = MaterialTheme.typography.titleLarge,
                            color = if (isDark) darkText else lightText
                        )
                    }
                },
                confirmButtonColor = Color.Red
            )
        }
        Scaffold(
            topBar = {
                SearchTopAppBar(
                    query = query,
                    onQueryChange = onQueryChange,
                    onSearch = onSearchQuerySubmitted,
                    onBackClick = onBackClick,
                    isDark = isDark
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .imePadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (query.isEmpty()) {
                    SearchHistory(
                        uiState = uiState,
                        onQueryChange = onQueryChange,
                        onSearchQuerySubmitted = onSearchQuerySubmitted,
                        onClearClick = {
                            showDeleteSearchHistoryDialog = true
                            keyboardController?.hide()
                        },
                        isDark = isDark
                    )
                } else {
                    if (playgrounds.isEmpty()) {
                        EmptySearch(
                            onClick = onBackClick,
                            isDark = isDark
                        )
                    } else {
                        SearchResults(
                            playgrounds = playgrounds,
                            query = query,
                            onQueryChange = onQueryChange,
                            onSearchQuerySubmitted = onSearchQuerySubmitted,
                            isDark = isDark
                        )
                    }
                }
            }
        }
    } else {
        var showBottomSheet by remember { mutableStateOf(false) }
        if (showBottomSheet) {
            MyModalBottomSheet(onDismissRequest = { showBottomSheet = false }) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.sort_by),
                        color = if (isDark) darkText else lightText,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(id = R.string.bookable),
                            color = if (isDark) darkText else lightText,
                            style = MaterialTheme.typography.titleLarge
                        )
                        IconButton(
                            onClick = {
                                onSearchFilterChanged(SearchFilter.Bookable)
                                showBottomSheet = false
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.sortascending),
                                contentDescription = null
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(id = R.string.rating),
                            color = if (isDark) darkText else lightText,
                            style = MaterialTheme.typography.titleLarge
                        )
                        IconButton(
                            onClick = {
                                onSearchFilterChanged(SearchFilter.BestRating)
                                showBottomSheet = false
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.sortascending),
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
        Scaffold(
            topBar = {
                MyTopAppBar(onBackClick = onBackClick, isDark = isDark) {
                    IconButton(
                        onClick = {
                            showBottomSheet = true
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.sortascending),
                            contentDescription = null
                        )
                    }
                }
            }
        ) {
            it
        }
    }
}

@Composable
fun SearchResults(
    playgrounds: List<Playground>,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearchQuerySubmitted: (String) -> Unit,
    isDark: Boolean
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(playgrounds.take(10)) { item ->
            SearchResultsItem(
                text = item.name,
                distance = String.format("%.1f", item.distance),
                onClick = {
                    onQueryChange(query)
                    onSearchQuerySubmitted(query)
                },
                isDark = isDark
            )
        }

        item {
            TextButton(
                onClick = { onSearchQuerySubmitted(query) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.show_all_results),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                )
            }

        }
    }
}

@Composable
fun SearchHistory(
    uiState: SearchUiState,
    onQueryChange: (String) -> Unit,
    onSearchQuerySubmitted: (String) -> Unit,
    onClearClick: () -> Unit,
    isDark: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.search_history),
            color = if (isDark) darkText else lightText,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = stringResource(id = R.string.remove),
            color = Color.Red,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.clickable {
                onClearClick()
            }
        )
    }
    LazyColumn {
        items(uiState.searchHistory) { item ->
            SearchHistoryItem(
                text = item,
                onClick = {
                    onQueryChange(item)
                    onSearchQuerySubmitted(item)
                },
                isDark = isDark,
            )
        }
    }
}

@Preview(name = "light", locale = "ar", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "dark", locale = "en", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SearchScreenPreview() {
    val mockSearchViewModel = MockSearchViewModel()
    KhomasiTheme {
        SearchScreen(
            searchUiState = mockSearchViewModel.uiState,
            playgroundsState = mockSearchViewModel.searchResults,
            searchQuery = mockSearchViewModel.searchQuery,
            onBackClick = {},
            onQueryChange = mockSearchViewModel::onSearchQueryChanged,
            onSearchQuerySubmitted = mockSearchViewModel::onSearchQuerySubmitted,
            onClearHistory = mockSearchViewModel::onClickRemoveSearchHistory,
            navigateToPlaygroundDetails = {},
            onSearchFilterChanged = mockSearchViewModel::onSearchFilterChanged,
            onBackPage = {},
            onNextPage = {},
        )
    }
}