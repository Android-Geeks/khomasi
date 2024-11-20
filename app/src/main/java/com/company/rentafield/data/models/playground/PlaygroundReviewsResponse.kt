package com.company.rentafield.data.models.playground


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaygroundReviewsResponse(
    @SerialName("reviewList")
    val reviewList: List<com.company.rentafield.data.models.playground.Review>
)