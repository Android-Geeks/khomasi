package com.company.khomasi.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaygroundReviewResponse(
    @SerialName("reviewList")
    val reviewList: List<Review>
)