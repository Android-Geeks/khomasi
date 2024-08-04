package com.company.rentafield.presentation.screens.myBookings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.MessageResponse
import com.company.rentafield.domain.model.booking.BookingDetails
import com.company.rentafield.domain.model.booking.MyBookingsResponse
import com.company.rentafield.domain.model.booking.PlaygroundReviewRequest
import com.company.rentafield.domain.use_case.local_user.LocalUserUseCases
import com.company.rentafield.domain.use_case.remote_user.RemoteUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyBookingViewModel @Inject constructor(
    private val remoteUserUseCase: RemoteUserUseCase,
    private val localUserUseCases: LocalUserUseCases,
) : ViewModel() {

    private val _uiState: MutableStateFlow<MyBookingUiState> = MutableStateFlow(MyBookingUiState())
    val uiState: StateFlow<MyBookingUiState> = _uiState

    private val _reviewState =
        MutableStateFlow<DataState<MessageResponse>>(DataState.Empty)
    val reviewState: StateFlow<DataState<MessageResponse>> = _reviewState

    private val _myBookingState: MutableStateFlow<DataState<MyBookingsResponse>> =
        MutableStateFlow(DataState.Empty)
    val myBookingState: StateFlow<DataState<MyBookingsResponse>> = _myBookingState

    fun myBookingPlaygrounds() {
        viewModelScope.launch {
            localUserUseCases.getLocalUser().collect { userData ->
                remoteUserUseCase.getUserBookingsUseCase(
                    "Bearer ${userData.token}",
                    userData.userID ?: ""
                ).collect { dataState ->
                    _myBookingState.update { dataState }
                    if (dataState is DataState.Success) {
                        _uiState.update {
                            _uiState.value.copy(
                                currentBookings = dataState.data.results.filter { bookingDetails ->
                                    !bookingDetails.isFinished
                                },
                                expiredBookings = dataState.data.results.filter { bookingDetails ->
                                    bookingDetails.isFinished
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    fun cancelBooking(bookingId: Int) {
        viewModelScope.launch {
            localUserUseCases.getLocalUser().collect { userData ->
                remoteUserUseCase.cancelBookingUseCase(
                    "Bearer ${userData.token}",
                    bookingId,
                    true
                ).collect {

                }
            }
        }
    }

    fun onRatingChange(rating: Float) {
        _uiState.value = _uiState.value.copy(rating = rating)
    }

    fun onCommentChange(comment: String) {
        _uiState.value = _uiState.value.copy(comment = comment)
    }

    fun onClickPlayground(bookingDetails: BookingDetails) {
        _uiState.value = _uiState.value.copy(
            cancelBookingDetails = bookingDetails
        )
    }

    fun playgroundReview() {
        viewModelScope.launch {
            localUserUseCases.getLocalUser().collect { userData ->
                remoteUserUseCase.playgroundReviewUseCase(
                    token = "Bearer ${userData.token}",
                    playgroundReview = PlaygroundReviewRequest(
                        playgroundId = _uiState.value.playgroundId,
                        userId = userData.userID ?: "",
                        comment = _uiState.value.comment,
                        rating = _uiState.value.rating.toInt(),
                    )
                ).collect { state ->
                    _reviewState.update { state }
                }
            }
        }
    }
}
