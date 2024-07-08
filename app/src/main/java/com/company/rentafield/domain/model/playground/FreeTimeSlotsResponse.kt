package com.company.rentafield.domain.model.playground

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FreeTimeSlotsResponse(
    @SerialName("freeTimeSlots")
    val freeTimeSlots: List<FreeTimeSlots>
)

@Serializable
data class FreeTimeSlots(
    @SerialName("start")
    var start: String,
    @SerialName("end")
    var end: String,
    @SerialName("duration")
    val duration: Double,
)