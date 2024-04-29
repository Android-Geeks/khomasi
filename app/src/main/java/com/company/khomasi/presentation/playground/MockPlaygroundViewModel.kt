package com.company.khomasi.presentation.playground

import androidx.lifecycle.ViewModel
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.PlaygroundScreenResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MockPlaygroundViewModel : ViewModel() {
    private val _playgroundState: MutableStateFlow<DataState<PlaygroundScreenResponse>> =
        MutableStateFlow(DataState.Empty)
    val playgroundState: StateFlow<DataState<PlaygroundScreenResponse>> = _playgroundState
    private val _uiState: MutableStateFlow<PlaygroundUiState> =
        MutableStateFlow(PlaygroundUiState())
    val uiState: StateFlow<PlaygroundUiState> = _uiState
    fun getPlaygroundDetails(playgroundId: Int) {

    }

    fun onBookNowClicked() {

    }
}
