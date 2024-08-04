package com.company.rentafield.presentation.screens.playground.booking

import org.threeten.bp.LocalDateTime


data class BookingUiState(
    val playgroundId: Int = 1,
    val playgroundName: String = "",
    val playgroundAddress: String = "",
    val playgroundPrice: Int = 0,
    val playgroundMainPicture: String = "",
    val totalPrice: Int = 0,
    val bookingTime: String = "",
    val selectedDuration: Int = 60,
    val selectedDay: Int = 0,
    val selectedSlots: MutableList<Pair<LocalDateTime, LocalDateTime>> = mutableListOf(),
)
