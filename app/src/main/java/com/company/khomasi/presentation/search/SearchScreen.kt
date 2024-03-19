package com.company.khomasi.presentation.search

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.domain.model.Playground
import com.company.khomasi.presentation.components.MyAlertDialog
import com.company.khomasi.presentation.components.MyModalBottomSheet
import com.company.khomasi.presentation.components.cards.PlaygroundCard
import com.company.khomasi.presentation.search.components.EmptySearch
import com.company.khomasi.presentation.search.components.MyTopAppBar
import com.company.khomasi.presentation.search.components.SearchFilterSheetContent
import com.company.khomasi.presentation.search.components.SearchHistory
import com.company.khomasi.presentation.search.components.SearchResults
import com.company.khomasi.presentation.search.components.SearchTopAppBar
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.theme.darkText
import com.company.khomasi.theme.lightText
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchUiState: StateFlow<SearchUiState>,
    playgroundsState: StateFlow<List<Playground>>,
    searchQuery: StateFlow<String>,
    onBackClick: () -> Unit,
    onQueryChange: (String) -> Unit,
    onSearchQuerySubmitted: (String) -> Unit,
    onClearHistory: () -> Unit,
    navigateToPlaygroundDetails: (Int) -> Unit,
    onSearchFilterChanged: (SearchFilter) -> Unit,
    isDark: Boolean = isSystemInDarkTheme(),
    onBackPage: () -> Unit,
    onNextPage: () -> Unit,
) {
    val uiState by searchUiState.collectAsState()
    val playgroundSearchResults by playgroundsState.collectAsState()
    val query by searchQuery.collectAsState()
    var showDeleteSearchHistoryDialog by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current
    var hideKeyboard by remember { mutableStateOf(false) }

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
                    TextButton(
                        onClick = { showDeleteSearchHistoryDialog = false },
                    ) {
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
                    onSearch = {
                        onSearchQuerySubmitted(query)
                        onNextPage()
                    },
                    onBackClick = onBackClick,
                    hideKeyboard = hideKeyboard,
                    onClearFocus = { hideKeyboard = false },
                    isDark = isDark
                )
            },
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    hideKeyboard = true
                }

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()

                    .padding(it)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .imePadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (query.isEmpty()) {
                    SearchHistory(
                        uiState = uiState,
                        onQueryChange = onQueryChange,
                        onSearchQuerySubmitted = onSearchQuerySubmitted,
                        onClearClick = {
                            keyboardController?.hide()
                            hideKeyboard = true
                            showDeleteSearchHistoryDialog = true
                        },
                        isDark = isDark
                    )
                } else {
                    if (playgroundSearchResults.isEmpty()) {
                        EmptySearch(
                            onClick = onBackClick,
                            isDark = isDark
                        )
                    } else {
                        SearchResults(
                            playgrounds = playgroundSearchResults,
                            query = query,
                            onQueryChange = onQueryChange,
                            onSearchQuerySubmitted = onSearchQuerySubmitted,
                            onNextPage = onNextPage,
                            isDark = isDark
                        )
                    }
                }
            }
        }
    } else {
        val bottomSheetState = rememberModalBottomSheetState()
        var showFilterSheet by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        var choice by remember { mutableStateOf(0) }
        val choices = listOf(
            stringResource(id = R.string.lowest_price),
            stringResource(id = R.string.rating),
            stringResource(id = R.string.nearest_to_me),
            stringResource(id = R.string.bookable)
        )

        if (showFilterSheet) {
            MyModalBottomSheet(
                sheetState = bottomSheetState,
                onDismissRequest = {
                    scope.launch {
                        showFilterSheet = false
                    }
                }) {
                SearchFilterSheetContent(
                    choice = choice,
                    onChoiceChange = { choice = it },
                    onSearchFilterChanged = onSearchFilterChanged,
                    choices = choices,
                    isDark = isDark,
                    hideBottomSheet = {
                        scope.launch {
                            showFilterSheet = false
                        }
                    },
                )
            }
        }
        Scaffold(
            topBar = {
                MyTopAppBar(onBackClick = onBackPage, isDark = isDark) {
                    IconButton(
                        onClick = {
                            scope.launch {
                                showFilterSheet = true
                            }
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (uiState.playgroundResults.isEmpty()) {
                    item {
                        EmptySearch(
                            onClick = onBackClick,
                            isDark = isDark
                        )
                    }
                } else {
                    items(uiState.playgroundResults) { item ->
                        PlaygroundCard(
                            playground = item,
                            onFavouriteClick = {},
                            onViewPlaygroundClick = { navigateToPlaygroundDetails(item.id) }
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                }
            }
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