package com.company.khomasi.presentation.myBookings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.LocalUser
import com.company.khomasi.domain.model.MyBookingsResponse
import com.company.khomasi.domain.use_case.local_user.LocalUserUseCases
import com.company.khomasi.domain.use_case.remote_user.RemoteUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyBookingViewModel @Inject constructor(
    private val remoteUserUseCase: RemoteUserUseCase,
    private val localUserUseCases: LocalUserUseCases,
): ViewModel()  {
    private val _myBooking =
        MutableStateFlow<DataState<MyBookingsResponse>>(DataState.Empty)
    val myBooking: StateFlow<DataState<MyBookingsResponse>> = _myBooking.asStateFlow()
    private val _uiState: MutableStateFlow<MyBookingUiState> = MutableStateFlow(MyBookingUiState())
    val uiState:StateFlow<MyBookingUiState> =_uiState.asStateFlow()
    private var localUser = LocalUser()

    fun myBookingPlaygrounds() {
        viewModelScope.launch {
            localUserUseCases.getLocalUser().collect {
                localUser = it
            remoteUserUseCase.getUserBookingsUseCase(
                "Bearer ${localUser.token}",
                localUser.userID ?: ""
            ).collect { dataState ->
                if (dataState is DataState.Success) {
                    Log.d("MyBookingViewModel", "Page: ${dataState.data.results.size}")

                    _uiState.value = _uiState.value.copy(
                        currentBookings = dataState.data.results.filter { bookingDetails ->
                            !bookingDetails.isFinished
                        },
                        expiredBookings = dataState.data.results.filter { bookingDetails ->
                            bookingDetails.isFinished
                        }
                    )
                }
            }
            }
        }
    }

    fun onClickPlayground(playgroundId: Int) {
        viewModelScope.launch {
            localUserUseCases.savePlaygroundId(playgroundId)
        }
    }
}
