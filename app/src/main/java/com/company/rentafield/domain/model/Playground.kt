package com.company.rentafield.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Playground(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("address")
    val address: String,
    @SerialName("rating")
    val rating: Double,
    @SerialName("isBookable")
    val isBookable: Boolean,
    @SerialName("feesForHour")
    val feesForHour: Int,
    @SerialName("distance")
    val distance: Double,
    @SerialName("isFav")
    val isFavourite: Boolean,
    @SerialName("playgroundPicture")
    val playgroundPicture: String?
)
