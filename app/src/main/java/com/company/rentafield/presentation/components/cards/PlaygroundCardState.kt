package com.company.rentafield.presentation.components.cards

data class PlaygroundCardState(
    val playground: com.company.rentafield.domain.models.playground.Playground,
    val isFavorite: Boolean
)
