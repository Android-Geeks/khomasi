package com.company.khomasi.presentation.myBookings

import androidx.lifecycle.ViewModel
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.MyBookingsResponse
import com.company.khomasi.domain.use_case.remote_user.RemoteUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.model.LocalUser

@HiltViewModel
class MyBookingViewModel @Inject constructor(
    private val remoteUserUseCase: RemoteUserUseCase
): ViewModel()  {
    private val _myBooking= MutableStateFlow<DataState<MyBookingsResponse>>(DataState.Empty)
    val myBooking : StateFlow<DataState<MyBookingsResponse>> = _myBooking
    private val _uiState: MutableStateFlow<MyBookingUiState>
       = MutableStateFlow(
            MyBookingUiState(
                playground = emptyList()
               // statusOfBooking = BookingStatus.EXPIRED
            )
        )
    val uiState:StateFlow<MyBookingUiState> =_uiState.asStateFlow()
    private var localUser = LocalUser()
init {
    myBookingPlaygrounds(localUser.token!!,localUser.userID!!)
}
    fun myBookingPlaygrounds(token: String,id: String) {
        viewModelScope.launch {
            remoteUserUseCase.getUserBookingsUseCase(token = token, id = id).collect {
                _myBooking.value = it
            }
        }
    }
}