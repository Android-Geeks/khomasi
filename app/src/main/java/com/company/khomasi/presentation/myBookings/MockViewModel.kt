package com.company.khomasi.presentation.myBookings

import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.MyBookingsResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.model.BookingDetails

class MyViewModel : ViewModel()  {
    private val _myBooking= MutableStateFlow<DataState<MyBookingsResponse>>(DataState.Empty)
    val myBooking : StateFlow<DataState<MyBookingsResponse>> = _myBooking
    private val _uiState: MutableStateFlow<MyBookingUiState>
            = MutableStateFlow(
        MyBookingUiState(
            playground = emptyList(),
            // statusOfBooking = BookingStatus.EXPIRED
        )
    )
    val uiState:StateFlow<MyBookingUiState> =_uiState.asStateFlow()

    fun myBookingPlaygrounds(userId: String) {
        viewModelScope.launch {
            _myBooking.value = DataState.Loading
            val mockBookingDetails = BookingDetails(
                bookingNumber = 123,
                playgroundId = 456,
                name = "Sample Playground",
                address = "Sample Address",
                bookingTime = "2024-03-20T12:00:00",
                duration = 2,
                cost = 50,
                confirmationCode = "ABC123",
                isCanceled = false
            )

            val mockResponse = MyBookingsResponse(results = listOf(mockBookingDetails))

            kotlinx.coroutines.delay(1000)
        }
    }
}
