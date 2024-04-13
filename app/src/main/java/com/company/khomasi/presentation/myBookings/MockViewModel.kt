package com.company.khomasi.presentation.myBookings

import androidx.lifecycle.ViewModel
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.MyBookingsResponse
import com.company.khomasi.domain.model.PlaygroundPicture
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MockViewModel : ViewModel() {
    private val _myBooking =
        MutableStateFlow<DataState<MyBookingsResponse>>(DataState.Empty)
    val myBooking : StateFlow<DataState<MyBookingsResponse>> = _myBooking
    private val _uiState: MutableStateFlow<MyBookingUiState> = MutableStateFlow(MyBookingUiState())
    val uiState:StateFlow<MyBookingUiState> =_uiState.asStateFlow()
    private val _myPic =
        MutableStateFlow<DataState<PlaygroundPicture>>(DataState.Empty)
    val myPic: StateFlow<DataState<PlaygroundPicture>> = _myPic.asStateFlow()

}
