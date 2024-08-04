package com.company.rentafield.presentation.screens.otp

data class OtpUiState(
    val code: String = "",
    val email: String = "",
    val timer: Int = 59,
    val isCodeCorrect: Boolean = true
)