package com.company.rentafield.presentation.venues


import androidx.lifecycle.ViewModel
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.FilteredPlaygroundResponse
import com.company.rentafield.domain.model.LocalUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.threeten.bp.LocalDateTime

class MockBrowseViewModel : ViewModel() {

    val localUser: StateFlow<LocalUser> = MutableStateFlow(LocalUser())


    private val _filteredPlaygrounds: MutableStateFlow<DataState<FilteredPlaygroundResponse>> =
        MutableStateFlow(DataState.Empty)
    val filteredPlaygrounds: StateFlow<DataState<FilteredPlaygroundResponse>> = _filteredPlaygrounds

    val uiState: StateFlow<BrowseUiState> = MutableStateFlow(
        BrowseUiState(
            price = 50,
            type = 5,
            bookingTime = "2024-09-01T00:00:00",
            duration = 1.0,
        )
    )

    fun getPlaygrounds() {

    }

    fun setPrice(price: Int) {
    }

    fun setBookingTime(bookingTime: LocalDateTime) {
    }

    fun setDuration(duration: Double) {
    }

    fun resetFilteredPlaygrounds() {
    }

    fun onFavouriteClicked(playgroundId: Int) {
    }

    fun updateDuration(type: String) {
        when (type) {
            "+" -> {


            }

            "-" -> {

            }
        }
    }
}