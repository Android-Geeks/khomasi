package com.company.rentafield.presentation.screens.search


data class SearchUiState(
    val searchFilter: SearchFilter = SearchFilter.LowestPrice,
    val searchHistory: List<String> = listOf(),
    val playgroundResults: List<com.company.rentafield.domain.models.playground.Playground> = listOf()
)

enum class SearchFilter {
    LowestPrice,
    Rating,
    Nearest,
    Bookable
}