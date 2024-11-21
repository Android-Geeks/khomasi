package com.company.rentafield.presentation.screens.playground

import androidx.lifecycle.ViewModel
import com.company.rentafield.domain.DataState
import com.company.rentafield.presentation.screens.playground.model.PlaygroundInfoUiState
import com.company.rentafield.presentation.screens.playground.model.PlaygroundReviewsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MockPlaygroundViewModel : ViewModel() {
    private val _playgroundState: MutableStateFlow<DataState<com.company.rentafield.domain.models.playground.PlaygroundScreenResponse>> =
        MutableStateFlow(DataState.Empty)
    val playgroundState: StateFlow<DataState<com.company.rentafield.domain.models.playground.PlaygroundScreenResponse>> =
        _playgroundState
    private val _uiState: MutableStateFlow<PlaygroundInfoUiState> =
        MutableStateFlow(PlaygroundInfoUiState())
    val uiState: StateFlow<PlaygroundInfoUiState> =
        _uiState
    private val _reviewsState: MutableStateFlow<DataState<com.company.rentafield.domain.models.playground.PlaygroundReviewsResponse>> =
        MutableStateFlow(DataState.Empty)
    val reviewsState: StateFlow<DataState<com.company.rentafield.domain.models.playground.PlaygroundReviewsResponse>> =
        _reviewsState

    private val _reviewsUiState: MutableStateFlow<PlaygroundReviewsUiState> =
        MutableStateFlow(PlaygroundReviewsUiState())
    val reviewsUiState: StateFlow<PlaygroundReviewsUiState> = _reviewsUiState
}