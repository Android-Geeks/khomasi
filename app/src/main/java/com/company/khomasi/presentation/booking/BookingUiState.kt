package com.company.khomasi.presentation.booking

import java.time.LocalDateTime

data class BookingUiState(
    val duration: Int = 60,
    val selectedDay: Int = 0,
    val selectedSlots: MutableList<Pair<LocalDateTime, LocalDateTime>> = mutableListOf(),
)
