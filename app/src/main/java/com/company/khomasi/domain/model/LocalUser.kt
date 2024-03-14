package com.company.khomasi.domain.model

data class LocalUser(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val city: String,
    val country: String,
    val latitude: Float,
    val longitude: Float,
    val profilePicture: String?,
    val coins: Int,
    val rating: Int,
    val userID: String,
    val token: String,
    val isLogin: Boolean = false,
    val isOnBoarding: Boolean = false
)
