package com.company.khomasi.presentation.favorite

import androidx.lifecycle.ViewModel
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.FavouritePlaygroundResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MockFavViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(FavouriteUiState())
    val uiState: StateFlow<FavouriteUiState> = _uiState

    private val _favState: MutableStateFlow<DataState<FavouritePlaygroundResponse>> =
        MutableStateFlow(DataState.Empty)
    val favState: StateFlow<DataState<FavouritePlaygroundResponse>> = _favState







}
