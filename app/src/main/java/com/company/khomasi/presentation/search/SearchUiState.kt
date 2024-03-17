package com.company.khomasi.presentation.search


data class SearchUiState(
    val searchQuery: String = "",
    val searchFilter: SearchFilter = SearchFilter.LowestPrice,
)

enum class SearchFilter {
    LowestPrice,
    BestRating,
    Nearest,
    Bookable
}