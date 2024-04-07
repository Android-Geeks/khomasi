package com.company.khomasi.presentation.myBookings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.LocalUser
import com.company.khomasi.domain.model.MyBookingsResponse
import com.company.khomasi.domain.use_case.remote_user.RemoteUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyBookingViewModel @Inject constructor(
    private val remoteUserUseCase: RemoteUserUseCase
): ViewModel()  {
    private val _myBooking= MutableStateFlow<DataState<MyBookingsResponse>>(DataState.Empty)
    val myBooking: StateFlow<DataState<MyBookingsResponse>> = _myBooking.asStateFlow()
    private val _uiState: MutableStateFlow<MyBookingUiState>
       = MutableStateFlow(
            MyBookingUiState(
                playground = emptyList()
            )
        )
    val uiState:StateFlow<MyBookingUiState> =_uiState.asStateFlow()
    private var localUser = LocalUser()
init {
    observeBookings()
}
//    fun myBookingPlaygrounds(token: String,id: String) {
//        viewModelScope.launch {
//            remoteUserUseCase.getUserBookingsUseCase(token = token, id = id).collect {
//                _myBooking.value = it
//            }
//        }
//    }

    suspend fun getUserBookings(token: String, id: String): Flow<DataState<MyBookingsResponse>> {
        return remoteUserUseCase.getUserBookingsUseCase(token, id)
    }

    fun observeBookings() {
        viewModelScope.launch {
            getUserBookings(localUser.token!!, localUser.userID!!).collect { dataState ->
                when (dataState) {
                    is DataState.Loading -> {

                    }

                    is DataState.Success -> {

                        dataState.data

                    }

                    is DataState.Error -> {

                        dataState.message

                    }

                    is DataState.Empty -> {


                    }
                }
            }
            }
        }
    }
