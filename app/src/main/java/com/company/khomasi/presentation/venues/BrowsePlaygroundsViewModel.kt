package com.company.khomasi.presentation.venues


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.FilteredPlaygroundResponse
import com.company.khomasi.domain.model.LocalUser
import com.company.khomasi.domain.use_case.local_user.LocalUserUseCases
import com.company.khomasi.domain.use_case.remote_user.RemotePlaygroundUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrowsePlaygroundsViewModel @Inject constructor(
    private val remotePlaygroundUseCase: RemotePlaygroundUseCase,
    private val localUserUseCases: LocalUserUseCases
) : ViewModel() {

    val localUser = localUserUseCases.getLocalUser().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), LocalUser()
    )

    private val _uiState = MutableStateFlow(BrowseUiState())
    val uiState: StateFlow<BrowseUiState> = _uiState

    private val _filteredPlaygrounds: MutableStateFlow<DataState<FilteredPlaygroundResponse>> =
        MutableStateFlow(DataState.Empty)
    val filteredPlaygrounds: StateFlow<DataState<FilteredPlaygroundResponse>> = _filteredPlaygrounds

    fun getPlaygrounds() {
        viewModelScope.launch(IO) {
            remotePlaygroundUseCase.getFilteredPlaygroundsUseCase(
                token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiYWxsQGdtYWlsLmNvbSIsImh0dHA6Ly9zY2hlbWFzLm1pY3Jvc29mdC5jb20vd3MvMjAwOC8wNi9pZGVudGl0eS9jbGFpbXMvcm9sZSI6IlVzZXIiLCJleHAiOjE3MTU5MTI2MTYsImlzcyI6IldlYkFQSURlbW8iLCJhdWQiOiJXZWJBUElEZW1vIn0.tpVcE0wWXn3lTzoqJ8aWnjgjcd0c315UVZDMveB4CFM",
                id = "c35eb9c6-92ff-4d23-99ad-66c98812e836",
                type = 5,
                price = 50,
                bookingTime = "2024-05-03T16:00:00",
                duration = 2.0,
            ).collect {
                _filteredPlaygrounds.value = it
            }
        }
    }

    fun setPrice(price: Int) {
        _uiState.value = _uiState.value.copy(price = price)
    }

    fun setBookingTime(bookingTime: String) {
        _uiState.value = _uiState.value.copy(bookingTime = bookingTime)
    }

    fun setDuration(duration: Double) {
        _uiState.value = _uiState.value.copy(duration = duration)
    }

    fun resetFilteredPlaygrounds() {
        _uiState.value = BrowseUiState()
    }

    fun updateDuration(type: String) {
        when (type) {
            "+" -> {
                val increasedDuration = _uiState.value.selectedDuration.plus(30)

                _uiState.update {
                    it.copy(
                        selectedDuration = if (increasedDuration < 3600) increasedDuration else 3500,   // always less than or equal to 3600 minutes to avoid overlapping time slots.
                    )
                }

            }

            "-" -> {
                _uiState.update {
                    val decreasedDuration = it.selectedDuration - 30    // 120 -> 90
                    it.copy(
                        selectedDuration = decreasedDuration,
                    )
                }
            }
        }
    }

    fun onFavouriteClicked(playgroundId: Int) {
//        if (_filteredPlaygrounds.value is DataState.Success) {
//            val playgrounds =
//                (_filteredPlaygrounds.value as DataState.Success).data.filteredPlaygrounds
//            val playground = playgrounds.find { it.id == playgroundId }
//            if (playground != null) {
//                _filteredPlaygrounds.value =
//                    DataState.Success(
//                        (_filteredPlaygrounds.value as DataState.Success).data.copy(
//                            filteredPlaygrounds = playgrounds.map {
//                                if (it.id == playgroundId) {
//                                    it.copy(
//                                        isFav = !it.isFav
//                                    )
//                                } else {
//                                    it
//                                }
//                            }
//                        )
//                    )
//            }
//        }
    }

}