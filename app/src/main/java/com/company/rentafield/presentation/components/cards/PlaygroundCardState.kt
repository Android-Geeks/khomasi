package com.company.rentafield.presentation.components.cards

import com.company.rentafield.domain.model.Playground

data class PlaygroundCardState(
    val playground: Playground,
    val isFavorite: Boolean
)
