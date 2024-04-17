package com.company.khomasi.presentation.booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.FessTimeSlotsResponse
import com.company.khomasi.domain.use_case.local_user.LocalUserUseCases
import com.company.khomasi.domain.use_case.remote_user.RemotePlaygroundUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
            localUserUseCases.getLocalUser().collect { userData ->
                localUserUseCases.getPlaygroundId().collect { playgroundId ->
                    remotePlaygroundUseCase.getFreeTimeSlotsUseCase(
                        token = "Bearer ${userData.token}",
                        id = playgroundId,
                        dayDiff = _bookingUiState.value.dayDiff
                    ).collect { playgroundsRes ->
                        _freeSlotsState.value = playgroundsRes
                    }
                }

            }
        }
    }

}