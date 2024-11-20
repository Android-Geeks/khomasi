package com.company.rentafield.data.models.ai


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AiLog(
    @SerialName("isProcessed")
    val isProcessed: Boolean,
    @SerialName("kickupCount")
    val kickupCount: Int,
    @SerialName("uploadDate")
    val uploadDate: String
)