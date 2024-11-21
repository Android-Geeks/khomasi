package com.company.rentafield.domain.models.user


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileImageResponse(
    @SerialName("profilePicture")
    val profilePicture: String?
)