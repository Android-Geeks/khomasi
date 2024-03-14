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
    val user: User
)

@Serializable
data class User(
    @SerialName("city")
    val city: String,
    @SerialName("coins")
    val coins: Int,
    @SerialName("country")
    val country: String,
    @SerialName("email")
    val email: String,
    @SerialName("firstName")
    val firstName: String,
    @SerialName("lastName")
    val lastName: String,
    @SerialName("latitude")
    val latitude: Float,
    @SerialName("longitude")
    val longitude: Float,
    @SerialName("phoneNumber")
    val phoneNumber: String,
    @SerialName("profilePicture")
    val profilePicture: String?,
    @SerialName("rating")
    val rating: Int,
    @SerialName("userID")
    val userID: String
)
