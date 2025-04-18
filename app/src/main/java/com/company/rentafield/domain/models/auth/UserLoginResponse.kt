package com.company.rentafield.domain.models.auth


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserLoginResponse(
    @SerialName("expiration")
    val expiration: String,
    @SerialName("token")
    val token: String,
    @SerialName("role")
    val role: String,
    @SerialName("user")
    val userLoginData: com.company.rentafield.domain.models.auth.UserLoginData
)

@Serializable
data class UserLoginData(
    @SerialName("city")
    val city: String,
    @SerialName("coins")
    val coins: Double,
    @SerialName("country")
    val country: String,
    @SerialName("email")
    val email: String,
    @SerialName("firstName")
    val firstName: String,
    @SerialName("lastName")
    val lastName: String,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("phoneNumber")
    val phoneNumber: String,
    @SerialName("rating")
    val rating: Double,
    @SerialName("userID")
    val userID: String
)
