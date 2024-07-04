package com.company.rentafield.domain.model.favourite


import com.company.rentafield.domain.model.playground.Playground
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavouritePlaygroundResponse(
    @SerialName("playgrounds")
    val playgrounds: List<Playground>,
    @SerialName("playgroundCount")
    val playgroundCount: Int
)