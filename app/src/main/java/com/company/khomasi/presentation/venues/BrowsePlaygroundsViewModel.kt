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
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
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
                token = "Bearer ${localUser.value.token ?: ""}",
                id = localUser.value.userID ?: "",
                price = _uiState.value.price,
                bookingTime = _uiState.value.bookingTime,
                duration = _uiState.value.duration
            ).collect {
                _filteredPlaygrounds.value = it
            }
        }
    }

    fun setPrice(price: Int) {
        _uiState.value = _uiState.value.copy(price = price)
    }

    fun setBookingTime(bookingTime: LocalDateTime) {
        _uiState.value = _uiState.value.copy(bookingTime = bookingTime)
    }

    fun setDuration(duration: Double) {
        _uiState.value = _uiState.value.copy(duration = duration)
    }

    fun resetFilteredPlaygrounds() {
        _uiState.value = BrowseUiState()
    }

    fun onFavouriteClicked(playgroundId: Int) {
        if (_filteredPlaygrounds.value is DataState.Success) {
            val playgrounds =
                (_filteredPlaygrounds.value as DataState.Success).data.filteredPlaygrounds
            val playground = playgrounds.find { it.id == playgroundId }
            if (playground != null) {
                _filteredPlaygrounds.value =
                    DataState.Success(
                        (_filteredPlaygrounds.value as DataState.Success).data.copy(
                            filteredPlaygrounds = playgrounds.map {
                                if (it.id == playgroundId) {
                                    it.copy(isFavourite = !it.isFavourite)
                                } else {
                                    it
                                }
                            }
                        )
                    )
            }
        }
    }

}