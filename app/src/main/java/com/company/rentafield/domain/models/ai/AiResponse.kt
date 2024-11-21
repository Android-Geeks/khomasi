package com.company.rentafield.domain.models.ai


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AiResponse(
    @SerialName("aiLogs")
    val aiLogs: List<com.company.rentafield.domain.models.ai.AiLog>
)