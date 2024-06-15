package com.company.rentafield.presentation.otpScreen

data class OtpUiState(
    val code: String = "",
    val email: String = "",
    val timer: Int = 59,
    val isCodeCorrect: Boolean = true
)