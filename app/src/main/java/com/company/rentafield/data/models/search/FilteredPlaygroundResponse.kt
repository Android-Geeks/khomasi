package com.company.rentafield.data.models.search


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilteredPlaygroundResponse(
    @SerialName("filteredPlaygrounds")
    val filteredPlaygrounds: List<com.company.rentafield.data.models.playground.Playground>
)