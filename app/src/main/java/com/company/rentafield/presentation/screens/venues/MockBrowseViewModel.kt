package com.company.rentafield.presentation.screens.venues


import androidx.lifecycle.ViewModel
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.LocalUser
import com.company.rentafield.domain.model.search.FilteredPlaygroundResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

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
}