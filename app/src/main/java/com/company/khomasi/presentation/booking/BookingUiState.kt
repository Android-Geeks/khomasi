package com.company.khomasi.presentation.booking

import java.time.LocalDateTime

data class BookingUiState(
    val playgroundId: Int = 1,
    val playgroundName: String = "",
    val playgroundAddress: String = "Nasr City, Cairo, Egypt",
    val playgroundPrice: Int = 0,
    val totalPrice: Int = 0,
    val bookingTime: String = "2024-04-23T12:00:00",
    val selectedDuration: Int = 60,
    val selectedDay: Int = 0,
    val selectedSlots: MutableList<Pair<LocalDateTime, LocalDateTime>> = mutableListOf(),
    val page: Int = 1
)
