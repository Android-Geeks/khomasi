package com.company.rentafield.domain.models


import androidx.compose.runtime.Immutable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class UserDataResponse(
    @SerialName("coins") val coins: Double,
    @SerialName("rating") val rating: Double
)