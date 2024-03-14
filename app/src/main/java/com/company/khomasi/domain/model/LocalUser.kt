package com.company.khomasi.domain.model

data class LocalUser(
    val userID: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val otpCode: Int? = null,
    val phoneNumber: String? = null,
    val city: String? = null,
    val country: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val profilePicture: String? = null,
    val coins: Int? = null,
    val rating: Int? = null,
    val token: String? = null,
)
