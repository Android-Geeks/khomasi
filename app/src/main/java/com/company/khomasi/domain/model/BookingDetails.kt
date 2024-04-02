package com.company.khomasi.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookingDetails(
    @SerialName("bookingNumber")
    val bookingNumber: Int,
    @SerialName("playgroundId")
    val playgroundId: Int,
    @SerialName("name")
    val name: String,
    @SerialName("address")
    val address: String,
    @SerialName("bookingTime")
    val bookingTime: String,
    @SerialName("duration")
    val duration: Int,
    @SerialName("cost")
    val cost: Int,
    @SerialName("confirmationCode")
    val confirmationCode: String,
    @SerialName("isCanceled")
    val isCanceled: Boolean
)