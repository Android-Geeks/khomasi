package com.company.khomasi.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaygroundReviewResponse(
    @SerialName("playgroundId")
    val playgroundId: Int,
    @SerialName("userId")
    val userId: String,
    @SerialName("comment")
    val comment: String,
    @SerialName("rating")
    val rating: Int,
    @SerialName("reviewTime")
    val reviewTime: String
)