package com.company.rentafield.presentation.screens.venues


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.usecases.localuser.LocalUserUseCases
import com.company.rentafield.domain.usecases.remoteuser.RemotePlaygroundUseCase
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
    localUserUseCases: LocalUserUseCases
) : ViewModel() {

    val localUser = localUserUseCases.getLocalUser().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000),
        com.company.rentafield.domain.models.LocalUser()
    )

    private val _uiState = MutableStateFlow(BrowseUiState())
    val uiState: StateFlow<BrowseUiState> = _uiState

    private val _filteredPlaygrounds: MutableStateFlow<DataState<com.company.rentafield.domain.models.search.FilteredPlaygroundResponse>> =
        MutableStateFlow(DataState.Empty)
    val filteredPlaygrounds: StateFlow<DataState<com.company.rentafield.domain.models.search.FilteredPlaygroundResponse>> =
        _filteredPlaygrounds

    fun getPlaygrounds() {
        viewModelScope.launch(IO) {
//            _filteredPlaygrounds.value = DataState.Loading
            var playgrounds: List<com.company.rentafield.domain.models.playground.Playground> =
                listOf()
            remotePlaygroundUseCase.getFilteredPlaygroundsUseCase(
                token = "Bearer ${localUser.value.token ?: ""}",
                id = localUser.value.userID ?: "",
                type = _uiState.value.type,
                price = _uiState.value.price,
                bookingTime = _uiState.value.bookingTime,
                duration = _uiState.value.duration,
            ).collect { filteredRes ->
                _filteredPlaygrounds.value = filteredRes
                if (filteredRes is DataState.Success) {
                    playgrounds = filteredRes.data.filteredPlaygrounds

                    /*                    _uiState.update {             ////        WILL BE IMPLEMENTED WHEN THE API HAS PRICES MORE THAN 50/////
                        it.copy(
                            maxValue = filteredRes.data.filteredPlaygrounds.maxOf { p -> p.feesForHour }
                                .toFloat()
                        )
                    }*/
                }
                if (filteredRes is DataState.Error) {
                    playgrounds = listOf()
                }
            }
            updatePlaygroundContent(playgrounds)
        }
    }

    fun updateType(type: Int) {
        _uiState.value = _uiState.value.copy(type = type)
    }

    fun setPrice(price: Int) {
        _uiState.value = _uiState.value.copy(price = price)
    }

    fun setBookingTime(bookingTime: String) {
        _uiState.value = _uiState.value.copy(bookingTime = bookingTime)
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

    private fun updateFilter(filter: SelectedFilter) {
        _uiState.value = _uiState.value.copy(
            selectedFilter = filter
        )
    }

    private fun updatePlaygroundContent(
        playgrounds: List<com.company.rentafield.domain.models.playground.Playground>,
    ) {
        _uiState.value = _uiState.value.copy(
            playgrounds = playgrounds,
            playgroundsResult = when (_uiState.value.selectedFilter) {
                SelectedFilter.Rating -> playgrounds.sortedByDescending { it.rating }
                SelectedFilter.Nearest -> playgrounds.sortedBy { it.distance }
                SelectedFilter.Bookable -> playgrounds.filter { it.isBookable }
            }
        )
    }

    fun onShowFiltersClicked(filter: SelectedFilter, bookingTime: String) {
        updateFilter(filter)
        setBookingTime(bookingTime)
        getPlaygrounds()
    }

    fun onResetFilters() {
        _uiState.value = BrowseUiState()
    }

    fun onFavouriteClicked(playgroundId: Int) {
        var updatedPlaygrounds: List<com.company.rentafield.domain.models.playground.Playground> =
            listOf()
        if (_filteredPlaygrounds.value is DataState.Success) {
            val playgrounds =
                (_filteredPlaygrounds.value as DataState.Success).data.filteredPlaygrounds
            val playground = playgrounds.find { it.id == playgroundId }
            if (playground != null) {
                updatedPlaygrounds = playgrounds.map {
                    if (it.id == playgroundId) {
                        it.copy(
                            isFavourite = !it.isFavourite
                        )
                    } else {
                        it
                    }
                }
                _filteredPlaygrounds.value =
                    DataState.Success(
                        (_filteredPlaygrounds.value as DataState.Success).data.copy(
                            filteredPlaygrounds = updatedPlaygrounds
                        )
                    )
            }
            updatePlaygroundContent(updatedPlaygrounds)
        }
    }
}