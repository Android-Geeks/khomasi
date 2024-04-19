package com.company.khomasi.presentation.booking


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.FessTimeSlotsResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime


class MockBookingViewModel : ViewModel() {
    private val _freeSlotsState: MutableStateFlow<DataState<FessTimeSlotsResponse>> =
        MutableStateFlow(DataState.Empty)
    val freeSlotsState: StateFlow<DataState<FessTimeSlotsResponse>> = _freeSlotsState

    private val _bookingUiState: MutableStateFlow<BookingUiState> =
        MutableStateFlow(BookingUiState())
    val bookingUiState: StateFlow<BookingUiState> = _bookingUiState

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
    }

    fun updateDuration(type: String) {

        when (type) {

            "+" -> {
                _bookingUiState.update {
                    val updatedDuration = it.selectedDuration + 30
                    if (updatedDuration > it.selectedSlots.size * 60) {

                        val nextSlot = it.nextSlot
                        if (nextSlot != null) {
                            it.selectedSlots.add(nextSlot)
                        }
                    }
                    it.copy(
                        selectedDuration = updatedDuration,
                        selectedSlots = it.selectedSlots
                    )

                }
            }

            "-" -> {
                _bookingUiState.update {
                    val decreasedDuration = it.selectedDuration - 30

                    it.copy(
                        selectedDuration = decreasedDuration,
                    )
                }
            }
        }
    }

    fun getTimeSlots() {

    }

    fun updateSelectedDay(day: Int = 0) {
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
    }
}
