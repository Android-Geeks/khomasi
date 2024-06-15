package com.company.rentafield.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaygroundReviewsResponse(
    @SerialName("reviewList")
    val reviewList: List<Review>
)