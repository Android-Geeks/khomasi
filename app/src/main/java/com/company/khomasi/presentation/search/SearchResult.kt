package com.company.khomasi.presentation.search

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.khomasi.R
import com.company.khomasi.presentation.components.MyModalBottomSheet
import com.company.khomasi.presentation.components.cards.PlaygroundCard
import com.company.khomasi.presentation.search.components.EmptySearch
import com.company.khomasi.presentation.search.components.MyTopAppBar
import com.company.khomasi.presentation.search.components.SearchFilterSheetContent
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResult(
    searchUiState: StateFlow<SearchUiState>,
    onBackClick: () -> Unit,
    navigateToPlaygroundDetails: (Int) -> Unit,
    onSearchFilterChanged: (SearchFilter) -> Unit,
    isDark: Boolean = isSystemInDarkTheme(),
    onBackPage: () -> Unit,
) {
    val uiState by searchUiState.collectAsStateWithLifecycle()

    val bottomSheetState = rememberModalBottomSheetState()
    var showFilterSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var choice by remember { mutableStateOf(0) }

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