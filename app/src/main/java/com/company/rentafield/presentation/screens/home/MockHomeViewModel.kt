package com.company.rentafield.presentation.screens.home

import androidx.lifecycle.ViewModel
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.playground.PlaygroundsResponse
import com.company.rentafield.presentation.screens.home.model.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update


class MockHomeViewModel : ViewModel() {

    private val _playgroundState: MutableStateFlow<DataState<PlaygroundsResponse>> =
        MutableStateFlow(DataState.Empty)
    val playgroundState: StateFlow<DataState<PlaygroundsResponse>> = _playgroundState

    private val _homeUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeUiState


    fun onClickViewAll() {
        _homeUiState.update {
            it.copy(
                viewAllSwitch = true
            )
        }
    }

}