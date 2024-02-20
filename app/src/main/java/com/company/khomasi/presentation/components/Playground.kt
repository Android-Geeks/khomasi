package com.company.khomasi.presentation.components

data class Playground(
    val name: String,
    val address: String,
    val imageUrl: String,
    val rating: Float,
    val price: String,
    val openingHours: String,
    val isFavorite: Boolean,
    val isBookable: Boolean
)

data class BookingDetails(
    val date: String,
    val time: String,
    val price: String,
    val verificationCode: String,
    val playground: Playground,
    val statusOfBooking: BookingStatus
)

enum class BookingStatus{
    PENDING,
    CONFIRMED,
    EXPIRED
}