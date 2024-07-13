package com.company.rentafield.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDataResponse(
    @SerialName("coins")
    val coins: Double,
    @SerialName("rating")
    val rating: Double
)