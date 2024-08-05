package com.company.rentafield.presentation.screens.myBookings

import androidx.lifecycle.ViewModel
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.MessageResponse
import com.company.rentafield.domain.model.booking.BookingDetails
import com.company.rentafield.domain.model.booking.MyBookingsResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MyBookingMockViewModel : ViewModel() {
    private val _myBooking =
        MutableStateFlow<DataState<MyBookingsResponse>>(
            DataState.Success(
                MyBookingsResponse(
                    results = listOf(
                        BookingDetails(
                            bookingNumber = 1,
                            playgroundId = 101,
                            playgroundName = "Playground A",
                            playgroundAddress = "123 Main Street",
                            playgroundPicture = "https://example.com/playground_a.jpg",
                            bookingTime = "2023-12-15T10:00:00",
                            duration = 1.5,
                            cost = 25,
                            confirmationCode = "ABC123",
                            isCanceled = false,
                            isFinished = false,
                            isFavorite = true
                        ),
                    )
                )
            )
        )
    val myBooking: StateFlow<DataState<MyBookingsResponse>> = _myBooking.asStateFlow()
    private val _uiState: MutableStateFlow<MyBookingUiState> = MutableStateFlow(
        MyBookingUiState(
            currentBookings = listOf(
                BookingDetails(
                    bookingNumber = 1,
                    playgroundId = 101,
                    playgroundName = "Playground A",
                    playgroundAddress = "123 Main Street",
                    playgroundPicture = "https://example.com/playground_a.jpg",
                    bookingTime = "2023-12-15T10:00:00",
                    duration = 1.5,
                    cost = 25,
                    confirmationCode = "ABC123",
                    isCanceled = false,
                    isFinished = false,
                    isFavorite = true
                ),
            ),
            expiredBookings = listOf(
                BookingDetails(
                    bookingNumber = 1,
                    playgroundId = 101,
                    playgroundName = "Playground A",
                    playgroundAddress = "123 Main Street",
                    playgroundPicture = "https://example.com/playground_a.jpg",
                    bookingTime = "2023-12-15T10:00:00",
                    duration = 1.5,
                    cost = 25,
                    confirmationCode = "ABC123",
                    isCanceled = false,
                    isFinished = false,
                    isFavorite = true
                ),
            ),
        )
    )
    val uiState: StateFlow<MyBookingUiState> = _uiState.asStateFlow()
    val reviewState: StateFlow<DataState<MessageResponse>> = MutableStateFlow(DataState.Empty)


}
