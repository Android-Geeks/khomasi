package com.company.rentafield.data.models.ai


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AiResponse(
    @SerialName("aiLogs")
    val aiLogs: List<com.company.rentafield.data.models.ai.AiLog>
)