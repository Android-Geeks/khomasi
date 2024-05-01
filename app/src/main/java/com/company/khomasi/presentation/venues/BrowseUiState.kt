package com.company.khomasi.presentation.venues

import org.threeten.bp.LocalDateTime


data class BrowseUiState(
    val price: Int = 50,
    val type: Int = 5,
    val bookingTime: LocalDateTime = LocalDateTime.now().plusHours(2),
    val duration: Double = 1.0,
)