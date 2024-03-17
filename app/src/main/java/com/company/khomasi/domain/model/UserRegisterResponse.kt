package com.company.khomasi.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserRegisterResponse(
    @SerialName("code")
    val code: Int,
    @SerialName("email")
    val email: String,
    @SerialName("message")
    val message: String
)

@Serializable
data class UserRegisterData(
    @SerialName("city")
    val city: String,
    @SerialName("country")
    val country: String,
    @SerialName("email")
    val email: String,
    @SerialName("name")
    val firstName: String,
    @SerialName("lastName")
    val lastName: String,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("password")
    val password: String,
    @SerialName("phoneNumber")
    val phoneNumber: String
)