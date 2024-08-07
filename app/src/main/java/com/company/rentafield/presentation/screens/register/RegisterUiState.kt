package com.company.rentafield.presentation.screens.register

data class RegisterUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val phoneNumber: String = "",
    val longitude: Double = 0.0,
    val latitude: Double = 0.0,
    val validating1: Boolean = false,
    val validating2: Boolean = false,
    val showLoading: Boolean = false
)
