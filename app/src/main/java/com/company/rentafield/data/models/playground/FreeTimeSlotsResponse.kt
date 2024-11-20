package com.company.rentafield.data.models.playground

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FreeTimeSlotsResponse(
    @SerialName("freeTimeSlots")
    val freeTimeSlots: List<com.company.rentafield.data.models.playground.FreeTimeSlots>
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