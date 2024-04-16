package com.company.khomasi.presentation.booking

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update


class BookingViewModel : ViewModel() {
    private val _bookingUiState: MutableStateFlow<BookingUiState> =
        MutableStateFlow(BookingUiState())
    val bookingUiState: StateFlow<BookingUiState> = _bookingUiState
    fun UpdateDuration(type: String) {
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
}