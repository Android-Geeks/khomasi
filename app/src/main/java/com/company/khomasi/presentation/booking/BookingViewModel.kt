package com.company.khomasi.presentation.booking

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.FessTimeSlotsResponse
import com.company.khomasi.domain.use_case.local_user.LocalUserUseCases
import com.company.khomasi.domain.use_case.remote_user.RemotePlaygroundUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val remotePlaygroundUseCase: RemotePlaygroundUseCase,
    private val localUserUseCases: LocalUserUseCases
) : ViewModel() {

    private val _freeSlotsState: MutableStateFlow<DataState<FessTimeSlotsResponse>> =
        MutableStateFlow(DataState.Empty)
    val freeSlotsState: StateFlow<DataState<FessTimeSlotsResponse>> = _freeSlotsState

    private val _bookingUiState: MutableStateFlow<BookingUiState> =
        MutableStateFlow(BookingUiState())
    val bookingUiState: StateFlow<BookingUiState> = _bookingUiState

    fun getFreeTimeSlots() {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                localUserUseCases.getLocalUser().collect { userData ->
                    localUserUseCases.getPlaygroundId().collect { playgroundId ->
                        remotePlaygroundUseCase.getFreeTimeSlotsUseCase(
                            token = "Bearer ${userData.token}",
                            id = 2,
                            dayDiff = _bookingUiState.value.selectedDay
                        ).collect { playgroundsRes ->
                            delay(350)
                            _freeSlotsState.value = playgroundsRes
                        }
                    }

                }
            }
        }
    }

    fun updateSelectedDay(day: Int) {
        _bookingUiState.update {
            it.copy(
                selectedDay = day
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateDuration(type: String) {

        when (type) {
            "+" -> {
                val increasedDuration = _bookingUiState.value.selectedDuration + 30  //90

                if (increasedDuration > _bookingUiState.value.selectedSlots.size * 60) {
                    _bookingUiState.value.nextSlot?.let { addSlot(it) }
                }
                _bookingUiState.update {
                    it.copy(
                        selectedDuration = increasedDuration,
                        selectedSlots = it.selectedSlots
                    )
                }
                val nexts = _bookingUiState.value.nextSlot!!
                _bookingUiState.value.selectedSlots.lastOrNull()?.let { currentSlot ->
                    getCurrentAndNextSlots(nexts, currentSlot)
                }
                Log.d(
                    "booking",
                    "selectedSlots after +: ${
                        (_bookingUiState.value.selectedSlots.map { pair ->
                            Pair(
                                formatTime(pair.first), formatTime(pair.second)
                            )
                        })
                    }"
                )
                Log.d(
                    "booking",
                    "currentSlot +: ${
                        Pair(_bookingUiState.value.currentSlot?.let { formatTime(it.first) },
                            _bookingUiState.value.currentSlot?.let { formatTime(it.second) })
                    } - nextSlot: ${
                        Pair(
                            _bookingUiState.value.nextSlot?.let { formatTime(it.first) },
                            _bookingUiState.value.nextSlot?.let {
                                formatTime(
                                    it.second
                                )
                            }
                        )
                    }"
                )

            }
            /*
             selectedSlots +: [(06:00 ص, 07:00 ص), (08:00 ص, 07:00 ص)]
             currentSlot +: (07:00 ص, 08:00 ص) - nextSlot: (07:00 ص, 08:00 ص)
             currentSlot: (07:00 ص, 08:00 ص) - nextSlot: (08:00 ص, 09:00 ص)
             selectedSlots after +: [(06:00 ص, 07:00 ص), (08:00 ص, 07:00 ص)]
             currentSlot +: (07:00 ص, 08:00 ص) - nextSlot: (08:00 ص, 09:00 ص)

             كله تمام بس بيعمل ابديت لل next بعد ما نظوس + مرتين مش مره واحده مش عارف ليه
            * */
            "-" -> {
                _bookingUiState.update {
                    val decreasedDuration = it.selectedDuration - 30    //90 -> 60

                    /*                    if (decreasedDuration < it.selectedSlots.size * 60 && !slotRemoved) {
                                            it.currentSlot.apply {
                                                if (this != null) {
                                                    onSlotAdded(this)
                                                }
                                            }
                                        }
                                        slotRemoved = true*/
                    it.copy(
                        selectedDuration = decreasedDuration,
                    )
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateNextSlot(nextSlot: Pair<LocalDateTime, LocalDateTime>) {
        _bookingUiState.update {
            it.copy(
                nextSlot = nextSlot
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentAndNextSlots(
        next: Pair<LocalDateTime, LocalDateTime>,
        current: Pair<LocalDateTime, LocalDateTime>
    ) {
        _bookingUiState.update {
            it.copy(
                nextSlot = if (it.selectedSlots.isEmpty()) null else next,
                currentSlot = if (it.selectedSlots.isEmpty()) null else current
            )
        }
        Log.d(
            "booking",
            "currentSlot: ${
                Pair(_bookingUiState.value.currentSlot?.let { formatTime(it.first) },
                    _bookingUiState.value.currentSlot?.let { formatTime(it.second) })
            } - nextSlot: ${
                Pair(
                    _bookingUiState.value.nextSlot?.let { formatTime(it.first) },
                    _bookingUiState.value.nextSlot?.let {
                        formatTime(
                            it.second
                        )
                    }
                )
            }"
        )
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun onSlotClicked(slot: Pair<LocalDateTime, LocalDateTime>) {
        _bookingUiState.update {

            addSlot(slot)
            it.copy(
                selectedSlots = it.selectedSlots,
                selectedDuration = if (it.selectedSlots.size > 1) it.selectedSlots.size * 60 else 60
            )
        }
        Log.d(
            "booking",
            "selectedSlots ck: ${
                (_bookingUiState.value.selectedSlots.map { pair ->
                    Pair(
                        formatTime(pair.first), formatTime(pair.second)
                    )
                })
            }"
        )

    }

}

