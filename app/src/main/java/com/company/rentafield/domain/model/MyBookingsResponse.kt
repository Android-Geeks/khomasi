package com.company.rentafield.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyBookingsResponse(
    @SerialName("userBookings")
    val results: List<BookingDetails>
)