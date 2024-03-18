package com.company.khomasi.presentation.search



data class SearchUiState(
    val searchFilter: SearchFilter = SearchFilter.LowestPrice,
    val searchHistory: List<String> = listOf(),
    val page: Int = 1
)

enum class SearchFilter {
    LowestPrice,
    BestRating,
    Nearest,
    Bookable
}