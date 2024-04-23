package com.company.khomasi.presentation.booking

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.FessTimeSlotsResponse
import com.company.khomasi.domain.use_case.local_user.LocalPlaygroundUseCase
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
    private val localUserUseCases: LocalUserUseCases,
    private val localPlaygroundUseCases: LocalPlaygroundUseCase
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
                            id = playgroundId,
                            dayDiff = _bookingUiState.value.selectedDay
                        ).collect { freeSlotsRes ->
                            delay(350)
                            _freeSlotsState.value = freeSlotsRes
                        }

                    }

                }
            }
        }
    }

    init {
        viewModelScope.launch {
            localPlaygroundUseCases.getPlaygroundName().collect { playgroundName ->
                localPlaygroundUseCases.getPlaygroundPrice().collect { playgroundPrice ->
                    _bookingUiState.update {
                        it.copy(
                            playgroundName = playgroundName,
                            playgroundPrice = playgroundPrice
                        )
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
                val increasedDuration = _bookingUiState.value.selectedDuration.plus(30)   //90
                val nextExpectedSlot = _bookingUiState.value.nextSlot
                _bookingUiState.value.selectedSlots.lastOrNull()?.let { currentSlot ->
                    if (nextExpectedSlot != null) {
                        updateCurrentAndNextSlots(next = nextExpectedSlot, current = currentSlot)
                    }
                }

                if (increasedDuration > _bookingUiState.value.selectedSlots.size * 60 && _bookingUiState.value.selectedSlots.size > 0) {
                    _bookingUiState.value.nextSlot?.let {
                        if (nextExpectedSlot != null) {
                            addSlot(it)
                        }
                    }
                }
                _bookingUiState.update {
                    it.copy(
                        selectedDuration = if (increasedDuration < 3600) increasedDuration else 3500,   // always less than or equal to 3600 minutes to avoid overlapping time slots.
                        selectedSlots = it.selectedSlots
                    )
                }

                /*                Log.d(
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
                                )*/
            }

            "-" -> {
                _bookingUiState.update {
                    val decreasedDuration = it.selectedDuration - 30    // 120 -> 90

                    if (decreasedDuration % 60 == 0 && it.selectedSlots.size > 1) {
                        it.selectedSlots.removeAt(it.selectedSlots.size - 1)
                    }

                    it.copy(
                        selectedDuration = decreasedDuration,
                        selectedSlots = it.selectedSlots
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
    fun updateCurrentAndNextSlots(
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
            "currentSlot get: ${
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

    @RequiresApi(Build.VERSION_CODES.O)
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

}

