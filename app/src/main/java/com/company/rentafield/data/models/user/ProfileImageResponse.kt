package com.company.rentafield.data.models.user


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileImageResponse(
    @SerialName("profilePicture")
    val profilePicture: String?
)