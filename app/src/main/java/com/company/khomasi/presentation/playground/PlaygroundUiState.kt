package com.company.khomasi.presentation.playground

import com.company.khomasi.domain.model.Playground


data class PlaygroundUiState(
    val isFavourite: Boolean = false,
    val showReviews: Boolean = false,
    val reviewsCount: Int = 0,
    val favPlayground: Playground = Playground
        (
        id = 20,
        name = "string",
        address = "string",
        rating = 5.0,
        isBookable = true,
        feesForHour = 50,
        distance = 12763.269606184858,
        isFavourite = false,
        playgroundPicture = null
    )
)
