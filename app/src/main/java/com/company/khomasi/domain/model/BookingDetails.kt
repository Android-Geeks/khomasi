package com.company.khomasi.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookingDetails(
    @SerialName("bookingNumber")
    val bookingNumber: Int,
    @SerialName("playgroundId")
    val playgroundId: Int,
    @SerialName("playgroundName")
    val playgroundName: String,
    @SerialName("playgroundAddress")
    val playgroundAddress: String,
    @SerialName("playgroundPicture")
    val playgroundPicture: String,
    @SerialName("bookingTime")
    val bookingTime: String,
    @SerialName("duration")
    val duration: Int,
    @SerialName("cost")
    val cost: Int,
    @SerialName("confirmationCode")
    val confirmationCode: String,
    @SerialName("isCanceled")
    val isCanceled: Boolean,
    @SerialName("isFinished")
    val isFinished: Boolean,
    @SerialName("isFavorite")
    val isFavorite: Boolean
)
