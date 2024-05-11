package com.company.khomasi.presentation.favorite

import com.company.khomasi.domain.model.Playground

data class FavouriteUiState(
    val playgrounds: List<Playground> = listOf(),
    val deletedPlaygroundIds: MutableSet<Int> = mutableSetOf(),
)