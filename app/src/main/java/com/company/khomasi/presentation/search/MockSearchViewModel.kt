package com.company.khomasi.presentation.search

import androidx.lifecycle.ViewModel
import com.company.khomasi.domain.model.Playground
import kotlinx.coroutines.flow.MutableStateFlow

class MockSearchViewModel : ViewModel() {

    val uiState = MutableStateFlow(SearchUiState())
    val searchResults = MutableStateFlow(emptyList<Playground>())
    val searchQuery = MutableStateFlow("")
    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
    }

    fun onSearchFilterChanged(filter: SearchFilter) {
        uiState.value = uiState.value.copy(searchFilter = filter)
    }

    fun onSearchQuerySubmitted(query: String) {

    }

    fun onClickRemoveSearchHistory() {

    }
}