package com.company.rentafield.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDataResponse(
    @SerialName("coins")
    val coins: Int,
    @SerialName("rating")
    val rating: Int
)