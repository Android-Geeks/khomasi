package com.company.rentafield.presentation.screens.myBookings

import com.company.rentafield.domain.model.booking.BookingDetails

data class MyBookingUiState(
    val currentBookings: List<BookingDetails> = listOf(),
    val expiredBookings: List<BookingDetails> = listOf(),
    val playgroundId: Int = 1,
    val rating: Float = 0f,
    val comment: String = " ",
    val isCanceled: Boolean = false,
    val cancelBookingDetails: BookingDetails = BookingDetails(
        bookingNumber = 0,
        playgroundId = 0,
        playgroundName = "",
        playgroundAddress = "",
        playgroundPicture = "",
        bookingTime = "",
        duration = 0.0,
        cost = 0,
        confirmationCode = "",
        isCanceled = true,
        isFinished = true,
        isFavorite = false
    )
)