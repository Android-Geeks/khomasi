package com.company.khomasi.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookingRequest(
    @SerialName("playgroundId")
    val playgroundId: Int,
    @SerialName("userId")
    val userId: String,
    @SerialName("bookingTime")
    val bookingTime: String,
    @SerialName("duration")
    val duration: Double
)