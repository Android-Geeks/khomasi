package com.company.khomasi.presentation.search

import com.company.khomasi.domain.model.Playground


data class SearchUiState(
    val searchFilter: SearchFilter = SearchFilter.LowestPrice,
    val searchHistory: List<String> = listOf(),
    val playgroundResults: List<Playground> = listOf()
)

enum class SearchFilter {
    LowestPrice,
    Rating,
    Nearest,
    Bookable
}