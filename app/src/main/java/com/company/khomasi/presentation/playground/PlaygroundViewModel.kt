package com.company.khomasi.presentation.playground

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.FessTimeSlotsResponse
import com.company.khomasi.domain.model.PlaygroundReviewsResponse
import com.company.khomasi.domain.model.PlaygroundScreenResponse
import com.company.khomasi.domain.use_case.local_user.LocalUserUseCases
import com.company.khomasi.domain.use_case.remote_user.RemotePlaygroundUseCase
import com.company.khomasi.domain.use_case.remote_user.RemoteUserUseCase
import com.company.khomasi.presentation.playground.booking.BookingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class PlaygroundViewModel @Inject constructor(
    private val remoteUserUseCase: RemoteUserUseCase,
    private val remotePlaygroundUseCase: RemotePlaygroundUseCase,
    private val localUserUseCases: LocalUserUseCases,
) : ViewModel() {

    private val _playgroundState: MutableStateFlow<DataState<PlaygroundScreenResponse>> =
        MutableStateFlow(DataState.Empty)
    val playgroundState: StateFlow<DataState<PlaygroundScreenResponse>> = _playgroundState

    private val _uiState: MutableStateFlow<PlaygroundUiState> =
        MutableStateFlow(PlaygroundUiState())
    val uiState: StateFlow<PlaygroundUiState> = _uiState

    private val _bookingUiState: MutableStateFlow<BookingUiState> =
        MutableStateFlow(BookingUiState())
    val bookingUiState: StateFlow<BookingUiState> = _bookingUiState

    private val _freeSlotsState: MutableStateFlow<DataState<FessTimeSlotsResponse>> =
        MutableStateFlow(DataState.Empty)
    val freeSlotsState: StateFlow<DataState<FessTimeSlotsResponse>> = _freeSlotsState

    private val _reviewsState: MutableStateFlow<DataState<PlaygroundReviewsResponse>> =
        MutableStateFlow(DataState.Empty)
    val reviewsState: StateFlow<DataState<PlaygroundReviewsResponse>> = _reviewsState


    fun getPlaygroundDetails(playgroundId: Int) {
        viewModelScope.launch {
            val localUser = localUserUseCases.getLocalUser().first()

            remoteUserUseCase.getSpecificPlaygroundUseCase(
                token = "Bearer ${localUser.token}",
                id = playgroundId
            ).collect { playgroundRes ->
                _playgroundState.value = playgroundRes
                if (playgroundRes is DataState.Success) {
                    val playground = playgroundRes.data.playground
                    val pictureList = playgroundRes.data.playgroundPictures
                    _bookingUiState.update {
                        it.copy(
                            playgroundId = playground.id,
                            playgroundName = playground.name,
                            playgroundAddress = playground.address,
                            playgroundPrice = playground.feesForHour,
                            playgroundMainPicture = if (pictureList.isNotEmpty()) pictureList[0].picture else "",
                            totalPrice = 0
                        )
                    }
                }
            }

            remotePlaygroundUseCase.getPlaygroundReviewsUseCase(
                token = "Bearer ${localUser.token}",
                id = playgroundId
            ).collect { reviewRes ->
                _reviewsState.value = reviewRes
                if (reviewRes is DataState.Success) {
                    _uiState.update {
                        it.copy(reviewsCount = reviewRes.data.reviewList.size)
                    }
                }
            }
        }
    }


    fun updateShowReviews() {
        _uiState.value = _uiState.value.copy(showReviews = !_uiState.value.showReviews)
    }


    //---------------------------------------BookingViewModel---------------------------------------

    fun getFreeTimeSlots() {
        viewModelScope.launch(Dispatchers.Main) {
            val userData = localUserUseCases.getLocalUser().first()

            remotePlaygroundUseCase.getFreeTimeSlotsUseCase(
                token = "Bearer ${userData.token}",
                id = _bookingUiState.value.playgroundId,
                dayDiff = _bookingUiState.value.selectedDay
            ).collect { freeSlotsRes ->
                _freeSlotsState.value = freeSlotsRes
                _bookingUiState.update {
                    it.copy(
                        totalPrice = 0
                    )
                }
            }
        }
    }

    fun updateSelectedDay(day: Int) {
        _bookingUiState.update {
            it.copy(
                selectedDay = day,
                selectedSlots = mutableListOf()      // clear selected slots when day is changed
            )
        }
    }

    fun updateDuration(type: String) {
        when (type) {
            "+" -> {
                val increasedDuration = _bookingUiState.value.selectedDuration.plus(30)   //90

                _bookingUiState.update {
                    it.copy(
                        selectedDuration = if (increasedDuration < 3600) increasedDuration else 3500,   // always less than or equal to 3600 minutes to avoid overlapping time slots.
                        selectedSlots = mutableListOf()
                    )
                }

            }

            "-" -> {
                _bookingUiState.update {
                    val decreasedDuration = it.selectedDuration - 30    // 120 -> 90
                    it.copy(
                        selectedDuration = decreasedDuration,
                        selectedSlots = mutableListOf()
                    )
                }
            }
        }
    }

    private fun addSlot(slot: Pair<LocalDateTime, LocalDateTime>) {
        _bookingUiState.update {

            if (it.selectedSlots.contains(slot)) {
                it.selectedSlots.remove(slot)
            } else {
                it.selectedSlots.add(slot)
            }
            it.copy(
                selectedSlots = it.selectedSlots,
            )
        }
    }

    fun onSlotClicked(slot: Pair<LocalDateTime, LocalDateTime>) {

        _bookingUiState.update {
            addSlot(slot)
            it.copy(
                selectedSlots = it.selectedSlots,
            )
        }
        calculateTotalCost()
    }

    fun checkSlotsConsecutive(): Boolean {
        val selectedTimes = _bookingUiState.value.selectedSlots.sortedBy { it.first }
        var temp = true
        selectedTimes.forEachIndexed { index, it ->
            if (index + 1 < selectedTimes.size) {
                if (it.second != selectedTimes[index + 1].first) {
                    temp = false
                }
            }
        }
        if (_bookingUiState.value.selectedSlots.size == 0) {
            temp = false
        }
        return temp
    }

    private fun calculateTotalCost() {

        val durationInHours =
            _bookingUiState.value.selectedDuration / 60.0

        val total =
            (_bookingUiState.value.selectedSlots.size) * _bookingUiState.value.playgroundPrice * durationInHours
        _bookingUiState.update {
            it.copy(totalPrice = total.toInt())
        }
    }

    fun updateBookingTime() {
        _bookingUiState.update {
            it.copy(
                bookingTime = it.selectedSlots.first().first.toString()
            )
        }
    }

    fun updateCardNumber(cardNumber: String) {
        _uiState.update {
            it.copy(cardNumber = cardNumber)
        }
    }

    fun updateCardValidationDate(cardValidationDate: String) {
        _uiState.update {
            it.copy(cardValidationDate = cardValidationDate)
        }
    }

    fun updateCardCvv(cardCvv: String) {
        _uiState.update {
            it.copy(cardCvv = cardCvv)
        }
    }

    fun updateUserFavourite(playgroundId: String, isFavourite: Boolean) {
        viewModelScope.launch {
            localUserUseCases.getLocalUser().collect { localUser ->
                Log.d("PlaygroundCardViewModel", "updateUserFavourite: $isFavourite")
                if (isFavourite) {
                    remoteUserUseCase.deleteUserFavoriteUseCase(
                        token = "Bearer ${localUser.token ?: ""}",
                        userId = localUser.userID ?: "",
                        playgroundId = playgroundId,
                    ).collect {
                        Log.d("PlaygroundCardViewModel", "updateUserFavourite: $it")
                    }
                } else {
                    remoteUserUseCase.userFavouriteUseCase(
                        token = "Bearer ${localUser.token ?: ""}",
                        userId = localUser.userID ?: "",
                        playgroundId = playgroundId,
                    ).collect {
                        Log.d("PlaygroundCardViewModel", "updateUserFavourite: $it")
                    }
                }
            }
        }
    }
}




