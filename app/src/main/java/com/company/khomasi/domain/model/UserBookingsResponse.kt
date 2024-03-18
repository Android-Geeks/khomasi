package com.company.khomasi.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserBookingsResponse(
    @SerialName("results")
    val results: List<Result>
)