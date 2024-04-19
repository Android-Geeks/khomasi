package com.company.khomasi.presentation.booking

import java.time.LocalDateTime

data class BookingUiState(
    val selectedDuration: Int = 60,
    val selectedDay: Int = 0,
    val selectedSlots: MutableList<Pair<LocalDateTime, LocalDateTime>> = mutableListOf(),
    val nextSlot: Pair<LocalDateTime, LocalDateTime>? = null,
    val currentSlot: Pair<LocalDateTime, LocalDateTime>? = null
)
