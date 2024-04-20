package com.company.khomasi.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeedbackRequest(
    @SerialName("userId")
    val userId: String,
    @SerialName("category")
    val category: String,
    @SerialName("content")
    val content: String
)