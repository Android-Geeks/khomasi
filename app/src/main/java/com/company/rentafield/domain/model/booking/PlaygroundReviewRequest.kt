package com.company.rentafield.domain.model.booking


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaygroundReviewRequest(
    @SerialName("playgroundId")
    val playgroundId: Int,
    @SerialName("userId")
    val userId: String,
    @SerialName("comment")
    val comment: String,
    @SerialName("rating")
    val rating: Int,
)