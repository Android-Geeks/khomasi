package com.company.rentafield.domain.model.ai


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AiResponse(
    @SerialName("aiLogs")
    val aiLogs: List<AiLog>
)