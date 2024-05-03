package com.company.khomasi.presentation.venues



data class BrowseUiState(
    val price: Int = 50,
    val type: Int = 5,
    val bookingTime: String = "2024-09-01T00:00:00Z",
    val duration: Double = 1.0,
    val selectedDuration: Int = 60,
)