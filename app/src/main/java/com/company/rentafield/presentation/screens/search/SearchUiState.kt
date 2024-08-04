package com.company.rentafield.presentation.screens.search

import com.company.rentafield.domain.model.playground.Playground


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