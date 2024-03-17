package com.company.khomasi.presentation.search

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.presentation.components.MyAlertDialog
import com.company.khomasi.presentation.search.components.SearchHistoryItem
import com.company.khomasi.presentation.search.components.SearchTopAppBar
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.theme.darkText
import com.company.khomasi.theme.lightText

@Composable
fun SearchScreen(
    uiState: SearchUiState,
    onBackClick: () -> Unit,
    onQueryChange: (String) -> Unit,
    onSearchQuerySubmitted: (String) -> Unit,
    onClearHistory: () -> Unit,
    onSearchFilterChanged: (SearchFilter) -> Unit,
    isDark: Boolean = isSystemInDarkTheme()
) {
    var showDeleteSearchHistoryDialog by remember { mutableStateOf(false) }
    if (showDeleteSearchHistoryDialog) {
        MyAlertDialog(
            title = R.string.clear_history_,
            text = R.string.confirm_clear_history,
            onDismissRequest = { showDeleteSearchHistoryDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    onClearHistory()
                    showDeleteSearchHistoryDialog = false
                }) {
                    Text(text = stringResource(id = R.string.clear))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteSearchHistoryDialog = false }) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            })
    }
    Scaffold(
        topBar = {
            SearchTopAppBar(
                query = uiState.searchQuery,
                onQueryChange = onQueryChange,
                onSearch = onSearchQuerySubmitted,
                onBackClick = onBackClick,
                isDark = isDark
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp, vertical = 8.dp)
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
                        showDeleteSearchHistoryDialog = true
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
                        isDark = isDark
                    )
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
            onBackClick = {},
            onQueryChange = mockSearchViewModel::onSearchQueryChanged,
            onSearchQuerySubmitted = mockSearchViewModel::onSearchQuerySubmitted,
            onSearchFilterChanged = mockSearchViewModel::onSearchFilterChanged,
            uiState = mockSearchViewModel.uiState.collectAsState().value,
            onClearHistory = mockSearchViewModel::onClickRemoveSearchHistory
        )
    }
}