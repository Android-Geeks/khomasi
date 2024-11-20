package com.company.rentafield.data.models

import androidx.compose.runtime.Stable

@Stable
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
    val coins: Double? = null,
    val rating: Double? = null,
    val token: String? = null,
)
