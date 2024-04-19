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

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateDuration(type: String) {
        var slotAdded = false
        var slotRemoved = false
        when (type) {
            "+" -> {
                _bookingUiState.update {
                    val increasedDuration = it.selectedDuration + 30  //90

                    if (increasedDuration > it.selectedSlots.size * 60 && !slotAdded) {
                        it.nextSlot.apply {
                            if (this != null) {
                                onSlotAdded(this)
                            }
                        }

                    }
                    slotAdded = true
                    it.copy(
                        selectedDuration = increasedDuration,
                    )
                }
            }
// شغالين بس فيهم مشاكل
            "-" -> {
                _bookingUiState.update {
                    val decreasedDuration = it.selectedDuration - 30    //90 -> 60

                    if (decreasedDuration < it.selectedSlots.size * 60 && !slotRemoved) {
                        it.currentSlot.apply {
                            if (this != null) {
                                onSlotAdded(this)
                            }
                        }
                    }
                    slotRemoved = true
                    it.copy(
                        selectedDuration = decreasedDuration,
                    )
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getNextAndPastSlots(
        next: Pair<LocalDateTime, LocalDateTime>,
        past: Pair<LocalDateTime, LocalDateTime>
    ) {
        _bookingUiState.update {
            it.copy(
                nextSlot = next,
                currentSlot = past
            )
        }
        Log.d(
            "booking", "nextSlot: ${
                Pair(
                    _bookingUiState.value.nextSlot?.let { formatTime(it.first) },
                    _bookingUiState.value.nextSlot?.let {
                        formatTime(
                            it.second
                        )
                    }
                )
            } - currentSlot: ${
                Pair(_bookingUiState.value.currentSlot?.let { formatTime(it.first) },
                    _bookingUiState.value.currentSlot?.let { formatTime(it.second) })
            }"
        )
    }

    fun updateSelectedDay(day: Int) {
        _bookingUiState.update {
            it.copy(
                selectedDay = day
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onSlotAdded(slot: Pair<LocalDateTime, LocalDateTime>) {
        _bookingUiState.update {

            if (it.selectedSlots.contains(slot)) {
                it.selectedSlots.remove(slot)
            } else {
                it.selectedSlots.add(slot)
            }
            it.copy(
                selectedSlots = it.selectedSlots,
                selectedDuration = if (it.selectedSlots.size > 1) it.selectedSlots.size * 60 else 60
            )
        }
        Log.d(
            "booking",
            "selectedSlots: ${
                (_bookingUiState.value.selectedSlots.map { pair ->
                    Pair(
                        formatTime(pair.first), formatTime(pair.second)
                    )
                })
            }"
        )
//        Log.d("booking", "selectedDuration: ${_bookingUiState.value.selectedDuration}")

        /*
        onSlotClicked: [
        (2024-04-18T03:00:00.565507900, 2024-04-18T04:00:00.565507900),
        (2024-04-18T04:00:00.565507900, 2024-04-18T05:00:00.565507900)
        ]
        */
    }

}

/*        val hourlyIntervalsList = _freeSlotsState.value.apply {
            if (this is DataState.Success) {
                data.freeTimeSlots.map { slot ->
                    val startTime = parseTimestamp(slot.start).withMinute(0).withSecond(0)
                    val endTime = parseTimestamp(slot.end).withMinute(0).withSecond(0)
                    val duration = Duration.between(startTime, endTime).toHours()

                    List(duration.toInt()) { i ->
                        val hourStartTime = startTime.plusHours(i.toLong())
                        val hourEndTime = hourStartTime.plusHours(1)
                        Pair(hourStartTime, hourEndTime)
                    }
                }.flatten()
            } else {
                return
            }
        }*/
