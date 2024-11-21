package com.company.rentafield.presentation.screens.mybookings

data class MyBookingUiState(
    val currentBookings: List<com.company.rentafield.domain.models.booking.BookingDetails> = listOf(),
    val expiredBookings: List<com.company.rentafield.domain.models.booking.BookingDetails> = listOf(),
    val playgroundId: Int = 1,
    val rating: Float = 0f,
    val comment: String = " ",
    val isCanceled: Boolean = false,
    val cancelBookingDetails: com.company.rentafield.domain.models.booking.BookingDetails = com.company.rentafield.domain.models.booking.BookingDetails(
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