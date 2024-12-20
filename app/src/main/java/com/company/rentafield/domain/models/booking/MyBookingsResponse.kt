package com.company.rentafield.domain.models.booking


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyBookingsResponse(
    @SerialName("userBookings")
    val results: List<com.company.rentafield.domain.models.booking.BookingDetails>
)