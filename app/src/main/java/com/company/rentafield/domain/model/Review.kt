package com.company.rentafield.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Review(
    @SerialName("comment")
    val comment: String,
    @SerialName("rating")
    val rating: Int,
    @SerialName("reviewTime")
    val reviewTime: String,
    @SerialName("userEmail")
    val userEmail: String,
    @SerialName("userName")
    val userName: String,
    @SerialName("userPicture")
    val userPicture: String?
)