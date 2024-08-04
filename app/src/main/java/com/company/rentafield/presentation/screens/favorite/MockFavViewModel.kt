package com.company.rentafield.presentation.screens.favorite

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MockFavViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(FavouriteUiState())
    val uiState: StateFlow<FavouriteUiState> = _uiState
}