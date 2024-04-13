package com.company.khomasi.presentation.myBookings

import com.company.khomasi.domain.model.BookingDetails

data class MyBookingUiState(
    val bookingPlayground: List<BookingDetails> = listOf(),

    )

//enum class BookingStatus{
//    PENDING,
//    CONFIRMED,
//    EXPIRED
//}

