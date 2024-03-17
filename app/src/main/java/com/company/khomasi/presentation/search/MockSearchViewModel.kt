package com.company.khomasi.presentation.search

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class MockSearchViewModel : ViewModel() {

    val uiState = MutableStateFlow(SearchUiState())
    fun onSearchQueryChanged(query: String) {
        uiState.value = uiState.value.copy(searchQuery = query)
    }

    fun onSearchFilterChanged(filter: SearchFilter) {
        uiState.value = uiState.value.copy(searchFilter = filter)
    }

    fun onSearchQuerySubmitted(query: String) {

    }

    fun onClickRemoveSearchHistory() {

    }
}