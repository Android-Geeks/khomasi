package com.company.khomasi.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerificationResponse(
    @SerialName("code")
    val code: Int,
    val email : String,
    @SerialName("message")
    val message: String
)