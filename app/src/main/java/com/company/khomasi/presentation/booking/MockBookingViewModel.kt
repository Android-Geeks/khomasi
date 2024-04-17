package com.company.khomasi.presentation.booking


import androidx.lifecycle.ViewModel
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.FessTimeSlotsResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update


class MockBookingViewModel : ViewModel() {
    private val _freeSlotsState: MutableStateFlow<DataState<FessTimeSlotsResponse>> =
        MutableStateFlow(DataState.Empty)
    val freeSlotsState: StateFlow<DataState<FessTimeSlotsResponse>> = _freeSlotsState

    private val _bookingUiState: MutableStateFlow<BookingUiState> =
        MutableStateFlow(BookingUiState())
    val bookingUiState: StateFlow<BookingUiState> = _bookingUiState
    fun updateDuration(type: String = "") {
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

    fun getTimeSlots() {

    }

    fun updateSelectedDay(day: Int = 0) {
        _bookingUiState.update {
            it.copy(
                selectedDay = day
            )
        }
    }

}
