package com.company.khomasi.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FessTimeSlotsResponse(
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