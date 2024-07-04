package com.company.rentafield.domain.model.booking


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyBookingsResponse(
    @SerialName("userBookings")
    val results: List<BookingDetails>
)