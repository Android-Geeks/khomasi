package com.company.khomasi.presentation.myBookings

import com.company.khomasi.domain.model.BookingDetails
import java.text.SimpleDateFormat
import java.util.Calendar

data class MyBookingUiState(
    val currentBookings: List<BookingDetails> = listOf(),
    val expiredBookings: List<BookingDetails> = listOf(),
    val playgroundId: Int = 1,
    val rating: Float = 0f,
    val comment: String = " ",
    val reviewTime: String = getCurrentDateTime(),
    val isCanceled: Boolean = false
)

private fun getCurrentDateTime(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return dateFormat.format(Calendar.getInstance().time)
}
