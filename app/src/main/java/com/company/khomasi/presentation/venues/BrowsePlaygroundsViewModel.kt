package com.company.khomasi.presentation.venues


import android.util.Log
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
//        Log.d("kkkok", "I AM HEREEEEEREERRERE")
        viewModelScope.launch(IO) {
            remotePlaygroundUseCase.getFilteredPlaygroundsUseCase(
                token = "Bearer ${localUser.value.token ?: ""}",
                id = localUser.value.userID ?: "",
                type = 5,
                price = _uiState.value.price,
                bookingTime = _uiState.value.bookingTime,
                duration = _uiState.value.duration,
            ).collect { filteredRes ->
                _filteredPlaygrounds.value = filteredRes
                if (filteredRes is DataState.Success) {
                    _uiState.update {
                        it.copy(
                            playgroundsResult = filteredRes.data.filteredPlaygrounds
                        )
                    }
                }
                if (filteredRes is DataState.Error) {
                    _uiState.update {
                        it.copy(
                            playgroundsResult = listOf()
                        )
                    }
                }
            }
        }
    }

    fun setPrice(price: Int) {
        _uiState.value = _uiState.value.copy(price = price)
        Log.d("kkkok", "setPrice: ${_uiState.value.price}")
    }

    private fun setBookingTime(bookingTime: String) {
        _uiState.value = _uiState.value.copy(bookingTime = bookingTime)
    }

    fun setDuration(duration: Double) {
        _uiState.value = _uiState.value.copy(duration = duration)
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

    private fun onFilterChanged(filter: SelectedFilter) {
        getPlaygrounds()
        _uiState.value = _uiState.value.copy(
            selectedFilter = filter,
            playgroundsResult22 = when (filter) {
                SelectedFilter.Rating -> _uiState.value.playgroundsResult.take(1)/*.sortedBy { it.rating }*/
                SelectedFilter.Nearest -> _uiState.value.playgroundsResult.sortedBy { it.distance }
                SelectedFilter.Bookable -> _uiState.value.playgroundsResult.filter { it.isBookable }
            }
        )
        Log.d(
            "kkkok",
            "onShowFiltersClicked: ${_uiState.value.playgroundsResult}"
        )
        Log.d("kkkok", "setPrice: ${_uiState.value.price}")
        Log.d("kkkok", "bookingTime: ${_uiState.value.bookingTime}")
    }

    fun onShowFiltersClicked(filter: SelectedFilter, bookingTime: String) {
        setBookingTime(bookingTime)
        onFilterChanged(filter)
    }

    fun onResetFilters() {
        _uiState.value = BrowseUiState()
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