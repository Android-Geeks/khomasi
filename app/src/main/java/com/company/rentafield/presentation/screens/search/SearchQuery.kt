package com.company.rentafield.presentation.screens.search

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.rentafield.R
import com.company.rentafield.domain.model.LocalUser
import com.company.rentafield.domain.model.playground.Playground
import com.company.rentafield.presentation.components.MyAlertDialog
import com.company.rentafield.presentation.screens.search.components.EmptySearch
import com.company.rentafield.presentation.screens.search.components.SearchHistory
import com.company.rentafield.presentation.screens.search.components.SearchResults
import com.company.rentafield.presentation.screens.search.components.SearchTopAppBar
import com.company.rentafield.theme.RentafieldTheme
import com.company.rentafield.theme.darkErrorColor
import com.company.rentafield.theme.lightErrorColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun SearchQuery(
    searchUiState: StateFlow<SearchUiState>,
    localUserState: StateFlow<LocalUser>,
    playgroundsState: StateFlow<List<Playground>>,
    searchQuery: StateFlow<String>,
    getSearchData: () -> Unit,
    onBackClick: () -> Unit,
    onNextPage: () -> Unit,
    onQueryChange: (String) -> Unit,
    onSearchQuerySubmitted: (String) -> Unit,
    navigateToPlaygroundDetails: (Int, Boolean) -> Unit,
    onClearHistory: () -> Unit,
    isDark: Boolean = isSystemInDarkTheme(),
) {
    val localUser by localUserState.collectAsStateWithLifecycle()
    val uiState by searchUiState.collectAsStateWithLifecycle()
    val playgroundSearchResults by playgroundsState.collectAsStateWithLifecycle()
    val query by searchQuery.collectAsStateWithLifecycle()
    var showDeleteSearchHistoryDialog by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current
    var hideKeyboard by remember { mutableStateOf(false) }

    LaunchedEffect(localUser) {
        getSearchData()
    }


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
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            },
            confirmButtonColor = if (isDark) darkErrorColor else lightErrorColor
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
                        navigateToPlaygroundDetails = navigateToPlaygroundDetails,
                        onNextPage = onNextPage,
                    )
                }
            }
        }

    }
}

@Preview(name = "DARK | EN", locale = "en", uiMode = UI_MODE_NIGHT_YES, showSystemUi = true)
@Preview(name = "LIGHT | AR", locale = "ar", uiMode = UI_MODE_NIGHT_NO, showSystemUi = true)
@Composable
fun SearchQueryPreview() {
    RentafieldTheme {
        SearchQuery(
            searchUiState = MutableStateFlow(SearchUiState()),
            localUserState = MutableStateFlow(LocalUser()),
            playgroundsState = MutableStateFlow(listOf()),
            searchQuery = MutableStateFlow(""),
            getSearchData = {},
            onBackClick = {},
            onNextPage = {},
            onQueryChange = {},
            onSearchQuerySubmitted = {},
            navigateToPlaygroundDetails = { _, _ -> },
            onClearHistory = {}
        )
    }
}
