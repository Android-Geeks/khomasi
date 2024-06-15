package com.company.rentafield.presentation.playground


data class PlaygroundUiState(
    val isFavourite: Boolean = false,
    val playgroundId: Int = 0,
    val showReviews: Boolean = false,
    val reviewsCount: Int = 0,
    val cardNumber: String = "",
    val cardValidationDate: String = "",
    val cardCvv: String = "",
    val coins: Double = 3000.0,
    val totalCoinPrice: Double = 0.0,
)

enum class PaymentType {
    Fawry,
    Visa,
    Coins,
    Empty
}
