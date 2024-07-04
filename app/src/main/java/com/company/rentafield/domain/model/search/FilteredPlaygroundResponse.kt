package com.company.rentafield.domain.model.search


import com.company.rentafield.domain.model.playground.Playground
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilteredPlaygroundResponse(
    @SerialName("filteredPlaygrounds")
    val filteredPlaygrounds: List<Playground>
)