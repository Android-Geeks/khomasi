package com.company.rentafield.presentation.favorite

import androidx.lifecycle.ViewModel
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.favourite.FavouritePlaygroundResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MockFavViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(FavouriteUiState())
    val uiState: StateFlow<FavouriteUiState> = _uiState

    private val _favState: MutableStateFlow<DataState<FavouritePlaygroundResponse>> =
        MutableStateFlow(DataState.Empty)
    val favState: StateFlow<DataState<FavouritePlaygroundResponse>> = _favState
}
