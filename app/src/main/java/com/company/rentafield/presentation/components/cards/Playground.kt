package com.company.rentafield.presentation.components.cards

enum class BookingStatus{
    CANCEL,
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