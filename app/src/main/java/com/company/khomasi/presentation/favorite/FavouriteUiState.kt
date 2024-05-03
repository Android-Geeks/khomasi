package com.company.khomasi.presentation.favorite

import com.company.khomasi.domain.model.Playground

data class FavouriteUiState(
    val playgrounds: List<Playground> = emptyList(),
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
