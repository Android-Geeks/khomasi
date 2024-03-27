package com.company.khomasi.presentation.favorite

import com.company.khomasi.domain.model.Playground

data class FavouriteUiState(
    val isFavorite: Boolean=true,
    val userId:String=" ",
    val playgroundId:String=" ",
    val favPlayground: List<Playground> = listOf(),
    )
