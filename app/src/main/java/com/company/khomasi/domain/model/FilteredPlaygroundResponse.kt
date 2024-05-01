package com.company.khomasi.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilteredPlaygroundResponse(
    @SerialName("filteredPlaygrounds")
    val filteredPlaygrounds: List<Playground>
)