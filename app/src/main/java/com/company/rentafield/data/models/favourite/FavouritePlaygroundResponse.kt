package com.company.rentafield.data.models.favourite


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavouritePlaygroundResponse(
    @SerialName("playgrounds")
    val playgrounds: List<com.company.rentafield.data.models.playground.Playground>,
    @SerialName("playgroundCount")
    val playgroundCount: Int
)