package com.company.khomasi.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserUpdateData(
    @SerialName("id")
    val id: String,
    @SerialName("firstName")
    val firstName: String,
    @SerialName("lastName")
    val lastName: String,
    @SerialName("phoneNumber")
    val phoneNumber: String,
    @SerialName("country")
    val country: String,
    @SerialName("city")
    val city: String,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("latitude")
    val latitude: Double
)