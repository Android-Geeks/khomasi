package com.company.khomasi.presentation.myBookings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.BookingDetails
import com.company.khomasi.domain.model.PlaygroundReviewResponse
import com.company.khomasi.domain.use_case.local_user.LocalUserUseCases
import com.company.khomasi.domain.use_case.remote_user.RemoteUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyBookingViewModel @Inject constructor(
    private val remoteUserUseCase: RemoteUserUseCase,
    private val localUserUseCases: LocalUserUseCases,
): ViewModel()  {
    private val _reviewState =
        MutableStateFlow<DataState<PlaygroundReviewResponse>>(DataState.Empty)
    val reviewState: StateFlow<DataState<PlaygroundReviewResponse>> = _reviewState

    private val _uiState: MutableStateFlow<MyBookingUiState> = MutableStateFlow(MyBookingUiState())
    val uiState:StateFlow<MyBookingUiState> =_uiState.asStateFlow()


    fun myBookingPlaygrounds() {
        viewModelScope.launch {
            localUserUseCases.getLocalUser().collect { userData ->
            remoteUserUseCase.getUserBookingsUseCase(
                "Bearer ${userData.token}",
                userData.userID ?: ""
            ).collect { dataState ->
                if (dataState is DataState.Success) {
                    _uiState.value = _uiState.value.copy(
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

    fun reBook(playgroundId: Int) {
        viewModelScope.launch {
            localUserUseCases.savePlaygroundId(playgroundId)
        }
    }

    fun playgroundReview() {
        viewModelScope.launch {
            localUserUseCases.getLocalUser().collect { userData ->
                remoteUserUseCase.playgroundReviewUseCase(
                    token = "Bearer ${userData.token}",
                    playgroundReview = PlaygroundReviewResponse(
                        playgroundId = _uiState.value.playgroundId,
                        userId = userData.userID ?: " ",
                        comment = _uiState.value.comment,
                        rating = _uiState.value.rating.toInt(),
                    )
                ).collect {
                    _reviewState.value = it

                }
            }
        }
        }


}
