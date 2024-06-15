package com.company.rentafield.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CancelBookingResponse(
    @SerialName("bookingNumber")
    val bookingNumber: Int,
    @SerialName("userId")
    val userId: String,
    @SerialName("playgroundId")
    val playgroundId: Int,
    @SerialName("bookingTime")
    val bookingTime: String,
    @SerialName("duration")
    val duration: Int,
    @SerialName("cost")
    val cost: Int,
    @SerialName("isCanceled")
    val isCanceled: Boolean
)