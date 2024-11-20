package com.company.rentafield.data.models.booking


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyBookingsResponse(
    @SerialName("userBookings")
    val results: List<com.company.rentafield.data.models.booking.BookingDetails>
)