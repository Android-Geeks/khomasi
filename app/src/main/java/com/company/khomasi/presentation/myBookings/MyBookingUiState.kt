package com.company.khomasi.presentation.myBookings

import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.MyBookingsResponse
import com.company.khomasi.domain.model.Playground
import kotlinx.serialization.SerialName

data class MyBookingUiState(
    val date: String=" ",
    val time: String=" ",
    val price: String=" ",
    val verificationCode: String=" ",
    val playground: List<BookedPlayground>,
    val userId:String=" "
   // val statusOfBooking: BookingStatus
)
data class BookedPlayground(
    val bookingNumber: Int=1,
    val playgroundId: Int=1,
    val name: String=" ",
    val address: String=" ",
    val bookingTime: String=" ",
    val duration: Int=1,
    val cost: Int=1,
    val confirmationCode: String="",
    val isCanceled: Boolean=false

)
//enum class BookingStatus{
//    PENDING,
//    CONFIRMED,
//    EXPIRED
//}

