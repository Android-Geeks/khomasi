package com.company.khomasi.presentation.components.cards

enum class BookingStatus{
    PENDING,
    CONFIRMED,
    EXPIRED
}

data class CommentDetails(
    val userName: String,
    val userImageUrl : String,
    val comment : String,
    val date: String,
    val time: String,
    val rating: Float,
)