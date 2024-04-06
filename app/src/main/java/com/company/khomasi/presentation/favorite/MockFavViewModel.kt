package com.company.khomasi.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.FavouritePlaygroundResponse
import com.company.khomasi.domain.model.Playground
import com.company.khomasi.domain.model.PlaygroundsResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MockFavViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(FavouriteUiState())
    // Initialize with an empty userId
    val uiState: StateFlow<FavouriteUiState> = _uiState

    private val _favouritePlaygroundsState =
        MutableStateFlow<DataState<FavouritePlaygroundResponse>>(DataState.Empty)
    val favouritePlaygroundsState= _favouritePlaygroundsState.asStateFlow()





}
