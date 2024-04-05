package com.company.khomasi.presentation.components.cards

import com.company.khomasi.domain.model.Playground

data class PlaygroundCardState(
    val playground: Playground,
    val isFavorite: Boolean,
    val isLoading: Boolean,
    val error: String? = null
)
