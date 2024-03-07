package com.company.khomasi.presentation.register

data class RegisterUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val phoneNumber: String = "",
    val longitude: Double? = null,
    val latitude: Double? = null,
    val page: Int = 1,
    val validating1: Boolean = false,
    val validating2: Boolean = false
)
