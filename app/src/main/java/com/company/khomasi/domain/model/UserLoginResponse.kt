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