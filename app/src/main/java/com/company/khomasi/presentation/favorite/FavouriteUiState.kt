package com.company.khomasi.presentation.favorite

import com.company.khomasi.domain.model.Playground

data class FavouriteUiState(
    val isLoading: Boolean = false,
    val playgrounds: List<Playground> = emptyList(),
    val error: String = ""
    )
