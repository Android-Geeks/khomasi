package com.company.rentafield.domain.models.auth


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerificationResponse(
    @SerialName("code")
    val code: Int,
    @SerialName("email")
    val email: String,
    @SerialName("message")
    val message: String
)