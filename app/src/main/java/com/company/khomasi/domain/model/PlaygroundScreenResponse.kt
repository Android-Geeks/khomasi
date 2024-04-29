package com.company.khomasi.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaygroundScreenResponse(
    @SerialName("playground")
    val playground: PlaygroundX,
    @SerialName("playgroundPictures")
    val playgroundPictures: List<PlaygroundPicture>
)

@Serializable
data class PlaygroundPicture(
    @SerialName("id")
    val id: Int,
    @SerialName("playgroundId")
    val playgroundId: Int,
    @SerialName("picture")
    val picture: String,
    @SerialName("isDocumentation")
    val isDocumentation: Boolean,
)


@Serializable
data class PlaygroundX(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String,
    @SerialName("advantages")
    val advantages: String,
    @SerialName("address")
    val address: String,
    @SerialName("type")
    val type: Int,
    @SerialName("rating")
    val rating: Double,
    @SerialName("country")
    val country: String,
    @SerialName("city")
    val city: String,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("holidays")
    val holidays: String,
    @SerialName("openingHours")
    val openingHours: String,
    @SerialName("feesForHour")
    val feesForHour: Int,
    @SerialName("cancellationFees")
    val cancellationFees: Int,
    @SerialName("isBookable")
    val isBookable: Boolean,
    @SerialName("rules")
    val rules: String,
)

@Serializable
data class BusyTime(
    @SerialName("bookingTime")
    val bookingTime: String,
    @SerialName("duration")
    val duration: Int
)