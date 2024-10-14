package com.company.rentafield.presentation.screens.booking.model

data class PaymentUiState(
    val cardNumber: String = "",
    val cardValidationDate: String = "",
    val cardCvv: String = "",
    val coins: Double = 3000.0,
    val totalCoinPrice: Double = 0.0,
)
