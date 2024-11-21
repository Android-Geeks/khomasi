package com.company.rentafield.domain.models.favourite


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavouritePlaygroundResponse(
    @SerialName("playgrounds")
    val playgrounds: List<com.company.rentafield.domain.models.playground.Playground>,
    @SerialName("playgroundCount")
    val playgroundCount: Int
)