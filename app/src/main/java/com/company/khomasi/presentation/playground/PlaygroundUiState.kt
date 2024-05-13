package com.company.khomasi.presentation.playground


data class PlaygroundUiState(
    val isFavourite: Boolean = false,
    val playgroundId: Int = 0,
    val showReviews: Boolean = false,
    val reviewsCount: Int = 0,
)
