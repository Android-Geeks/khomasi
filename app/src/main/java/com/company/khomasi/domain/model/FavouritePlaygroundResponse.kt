package com.company.khomasi.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavouritePlaygroundResponse(
    @SerialName("playgrounds")
    val playgrounds: List<Playground>,
    @SerialName("playgroundCount")
    val playgroundCount: Int
)