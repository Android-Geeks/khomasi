package com.company.rentafield.presentation.screens.booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.usecases.localuser.LocalUserUseCases
import com.company.rentafield.domain.usecases.remoteuser.RemotePlaygroundUseCase
import com.company.rentafield.domain.usecases.remoteuser.RemoteUserUseCases
import com.company.rentafield.presentation.screens.booking.model.PaymentUiState
import com.company.rentafield.utils.parseTimestamp
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
class BookingViewModel @Inject constructor(
    private val remoteUserUseCases: RemoteUserUseCases,
    private val remotePlaygroundUseCase: RemotePlaygroundUseCase,
    private val localUserUseCases: LocalUserUseCases,
) : ViewModel() {

    private val _playgroundState: MutableStateFlow<DataState<com.company.rentafield.domain.models.playground.PlaygroundScreenResponse>> =
        MutableStateFlow(DataState.Empty)
    val playgroundState: StateFlow<DataState<com.company.rentafield.domain.models.playground.PlaygroundScreenResponse>> =
        _playgroundState

    private val _bookingResponse =
        MutableStateFlow<DataState<com.company.rentafield.domain.models.booking.BookingPlaygroundResponse>>(
            DataState.Empty
        )
    val bookingResponse: StateFlow<DataState<com.company.rentafield.domain.models.booking.BookingPlaygroundResponse>> =
        _bookingResponse

    private val _bookingUiState: MutableStateFlow<BookingUiState> =
        MutableStateFlow(BookingUiState())
    val bookingUiState: StateFlow<BookingUiState> = _bookingUiState

    private val _freeSlotsState: MutableStateFlow<DataState<com.company.rentafield.domain.models.playground.FreeTimeSlotsResponse>> =
        MutableStateFlow(DataState.Empty)
    val freeSlotsState: StateFlow<DataState<com.company.rentafield.domain.models.playground.FreeTimeSlotsResponse>> =
        _freeSlotsState

    private val _paymentUiState: MutableStateFlow<PaymentUiState> =
        MutableStateFlow(PaymentUiState())
    val paymentUiState: StateFlow<PaymentUiState> = _paymentUiState

    fun getPlaygroundDetails(playgroundId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val localUser = localUserUseCases.getLocalUser().first()

            remoteUserUseCases.getSpecificPlaygroundUseCase(
                token = "Bearer ${localUser.token}", id = playgroundId
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
                            totalPrice = 0,
                            hourlyIntervalList = calculateHourlyIntervalsList()
                        )
                    }
                    _paymentUiState.update {
                        it.copy(
                            coins = localUser.coins ?: 0.0,
                        )
                    }
                }
            }
        }
    }

    fun getFreeTimeSlots() {
        viewModelScope.launch(Dispatchers.IO) {
            val userData = localUserUseCases.getLocalUser().first()

            remotePlaygroundUseCase.getFreeTimeSlotsUseCase(
                token = "Bearer ${userData.token}",
                id = _bookingUiState.value.playgroundId,
                dayDiff = _bookingUiState.value.selectedDay
            ).collect { freeSlotsRes ->
                _freeSlotsState.value = freeSlotsRes
                _bookingUiState.update {
                    it.copy(
                        totalPrice = 0, hourlyIntervalList = calculateHourlyIntervalsList()
                    )
                }
            }
        }
    }

    private fun calculateHourlyIntervalsList(): List<Pair<LocalDateTime, LocalDateTime>> {
        val selectedDuration = _bookingUiState.value.selectedDuration

        return if (_freeSlotsState.value is DataState.Success<com.company.rentafield.domain.models.playground.FreeTimeSlotsResponse>) {

            (_freeSlotsState.value as DataState.Success<com.company.rentafield.domain.models.playground.FreeTimeSlotsResponse>).data.freeTimeSlots.mapIndexed { _, daySlots ->
                val startTime = parseTimestamp(daySlots.start).withMinute(0).withSecond(0)
                val endTime = parseTimestamp(daySlots.end).withMinute(0).withSecond(0)

                val startEndDuration =
                    if (endTime.hour > startTime.hour) {
                        (endTime.hour - startTime.hour) * 60
                    } else {
                        if (endTime.hour < startTime.hour) {
                            (24 - startTime.hour + endTime.hour) * 60
                        } else {
                            24 * 60
                        }
                    }

                val slotsCount = startEndDuration / selectedDuration
                List(slotsCount) { i ->
                    val hourStartTime = startTime.plusMinutes(i * selectedDuration.toLong())
                    val hourEndTime = hourStartTime.plusMinutes(selectedDuration.toLong())
                    Pair(hourStartTime, hourEndTime)
                }
            }.flatten()

        } else {
            emptyList()
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
                        selectedDuration = if (increasedDuration < 1440) increasedDuration else 1440,   // always less than or equal to 3600 minutes to avoid overlapping time slots.
                        selectedSlots = mutableListOf(),
                    )
                }

            }

            "-" -> {
                _bookingUiState.update {
                    val decreasedDuration = it.selectedDuration - 30    // 120 -> 90
                    it.copy(
                        selectedDuration = decreasedDuration,
                        selectedSlots = mutableListOf(),
                    )
                }
            }

        }
        _bookingUiState.update {
            it.copy(
                hourlyIntervalList = calculateHourlyIntervalsList()
            )
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

        val durationInHours = _bookingUiState.value.selectedDuration / 60.0

        val total =
            (_bookingUiState.value.selectedSlots.size) * _bookingUiState.value.playgroundPrice * durationInHours
        _bookingUiState.update {
            it.copy(totalPrice = total.toInt())
        }
        _paymentUiState.update {
            it.copy(
                totalCoinPrice = total
            )
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
        _paymentUiState.update {
            it.copy(cardNumber = cardNumber)
        }
    }

    fun updateCardValidationDate(cardValidationDate: String) {
        _paymentUiState.update {
            it.copy(cardValidationDate = cardValidationDate)
        }
    }

    fun updateCardCvv(cardCvv: String) {
        _paymentUiState.update {
            it.copy(cardCvv = cardCvv)
        }
    }

    fun bookingPlayground() {
        viewModelScope.launch(Dispatchers.IO) {
            val userData = localUserUseCases.getLocalUser().first()
            val bookingData = _bookingUiState.value
            val localUser = localUserUseCases.getLocalUser().first()

            remotePlaygroundUseCase.bookingPlaygroundUseCase(
                token = "Bearer ${userData.token}",
                body = com.company.rentafield.domain.models.playground.BookingRequest(
                    playgroundId = bookingData.playgroundId,
                    userId = userData.userID ?: "",
                    bookingTime = bookingData.bookingTime,
                    duration = (bookingData.totalPrice / bookingData.playgroundPrice).toDouble(),
                )
            ).collect { bookingRes ->
                _bookingResponse.value = bookingRes

                if (bookingRes is DataState.Success) {
                    localUserUseCases.saveLocalUser(
                        userData.copy(
                            coins = userData.coins?.minus(
                                bookingData.totalPrice
                            )
                        )
                    )
                    _paymentUiState.update {
                        it.copy(
                            coins = localUser.coins?.minus(
                                bookingData.totalPrice
                            ) ?: 0.0,
                        )
                    }
                }
            }

        }
    }

    /*fun updateUserFavourite(isFavourite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            localUserUseCases.getLocalUser().collect { localUser ->
                Log.d("PlaygroundCardViewModel", "updateUserFavourite: $isFavourite")
                if (isFavourite) {
                    remoteUserUseCase.deleteUserFavoriteUseCase(
                        token = "Bearer ${localUser.token ?: ""}",
                        userId = localUser.userID ?: "",
                        playgroundId = _paymentUiState.value.playgroundId,
                    ).collect {
                        if (it is DataState.Success) {
                            _paymentUiState.update { state ->
                                state.copy(
                                    isFavourite = false
                                )
                            }
                        }
                    }
                } else {
                    remoteUserUseCase.userFavouriteUseCase(
                        token = "Bearer ${localUser.token ?: ""}",
                        userId = localUser.userID ?: "",
                        playgroundId = _paymentUiState.value.playgroundId,
                    ).collect {
                        if (it is DataState.Success) {
                            _paymentUiState.update { state ->
                                state.copy(
                                    isFavourite = true
                                )
                            }
                        }
                    }
                }
            }
        }
    }*/

}
