package com.company.khomasi.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserLoginResponse(
    @SerialName("expiration")
    val expiration: String,
    @SerialName("token")
    val token: String,
    @SerialName("user")
    val userLoginData: UserLoginData
)

@Serializable
data class UserLoginData(
    @SerialName("city")
    val city: String,
    @SerialName("coins")
    val coins: Int,
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
    @SerialName("phoneNumber")
    val phoneNumber: String,
    @SerialName("profilePicture")
    val profilePicture: String?,
    @SerialName("rating")
    val rating: Int,
    @SerialName("userID")
    val userID: String
)
