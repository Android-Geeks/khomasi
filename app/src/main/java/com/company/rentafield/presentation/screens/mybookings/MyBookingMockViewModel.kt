package com.company.rentafield.presentation.screens.mybookings

import androidx.lifecycle.ViewModel
import com.company.rentafield.domain.DataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MyBookingMockViewModel : ViewModel() {
    private val _myBooking =
        MutableStateFlow<DataState<com.company.rentafield.domain.models.booking.MyBookingsResponse>>(
            DataState.Success(
                com.company.rentafield.domain.models.booking.MyBookingsResponse(
                    results = listOf(
                        com.company.rentafield.domain.models.booking.BookingDetails(
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
    val myBooking: StateFlow<DataState<com.company.rentafield.domain.models.booking.MyBookingsResponse>> =
        _myBooking.asStateFlow()
    private val _uiState: MutableStateFlow<MyBookingUiState> = MutableStateFlow(
        MyBookingUiState(
            currentBookings = listOf(
                com.company.rentafield.domain.models.booking.BookingDetails(
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
                com.company.rentafield.domain.models.booking.BookingDetails(
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
    val reviewState: StateFlow<DataState<com.company.rentafield.domain.models.MessageResponse>> =
        MutableStateFlow(DataState.Empty)


}
