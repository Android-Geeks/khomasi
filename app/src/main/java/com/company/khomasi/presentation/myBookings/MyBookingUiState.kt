package com.company.khomasi.presentation.myBookings

import com.company.khomasi.domain.model.BookingDetails
data class MyBookingUiState(
    val currentBookings: List<BookingDetails> = listOf(),
    val expiredBookings: List<BookingDetails> = listOf(),
    val isConfirmationPage: Boolean = false,
    val page: Int = 1
)


