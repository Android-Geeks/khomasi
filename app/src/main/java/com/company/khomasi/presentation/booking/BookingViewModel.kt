package com.company.khomasi.presentation.booking

import android.util.Log
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
    fun updateDuration(type: String) {
        when (type) {
            "+" -> {
                _bookingUiState.update {
                    it.copy(
                        duration = it.duration + 30
                    )
                }
            }

            "-" -> {
                _bookingUiState.update {
                    it.copy(
                        duration = it.duration - 30
                    )
                }
            }
        }
    }

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

    fun onSlotClicked(slot: Pair<LocalDateTime, LocalDateTime>) {
        _bookingUiState.update {
            it.copy(
                selectedSlots = _bookingUiState.value.selectedSlots.apply { add(slot) }
            )
        }
        Log.d("selectedSlot", "onSlotClicked: ${_bookingUiState.value.selectedSlots}")
    }

}