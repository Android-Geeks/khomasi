package com.company.rentafield.presentation.favorite

import com.company.rentafield.domain.model.playground.Playground

data class FavouriteUiState(
    val playgrounds: List<Playground> = listOf(),
)