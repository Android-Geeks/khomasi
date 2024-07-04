package com.company.rentafield.domain.model.playground


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaygroundsResponse(
    @SerialName("playgrounds")
    val playgrounds: List<Playground>,
    @SerialName("playgroundCount")
    val playgroundCount: Int
)