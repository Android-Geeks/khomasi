package com.company.khomasi.presentation.components.cards

import com.company.khomasi.domain.model.Playground

//data class Playground(
//    val name: String,
//    val address: String,
//    val imageUrl: String?,
//    val rating: Float,
//    val price: String,
//    val openingHours: String,
//    val isFavorite: Boolean,
//    val isBookable: Boolean
//)

//data class BookingDetails(
//    val date: String,
//    val time: String,
//    val price: String,
//    val verificationCode: String,
//    val playground: Playground,
//    val statusOfBooking: BookingStatus
//)

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