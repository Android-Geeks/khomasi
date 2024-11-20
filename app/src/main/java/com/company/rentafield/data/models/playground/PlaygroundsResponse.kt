package com.company.rentafield.data.models.playground


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaygroundsResponse(
    @SerialName("playgrounds")
    val playgrounds: List<com.company.rentafield.data.models.playground.Playground>,
    @SerialName("playgroundCount")
    val playgroundCount: Int
)