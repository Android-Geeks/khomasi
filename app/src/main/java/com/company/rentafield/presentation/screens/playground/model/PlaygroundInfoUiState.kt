package com.company.rentafield.presentation.screens.playground.model


data class PlaygroundInfoUiState(
    val isFavourite: Boolean = false,
    val playgroundId: Int = 0,
    val cardNumber: String = "",
    val cardValidationDate: String = "",
    val cardCvv: String = "",
    val coins: Double = 3000.0,
    val totalCoinPrice: Double = 0.0,
)

