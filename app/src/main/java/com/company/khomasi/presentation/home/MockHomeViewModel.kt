package com.company.khomasi.presentation.home

import androidx.lifecycle.ViewModel
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.LocalUser
import com.company.khomasi.domain.model.PlaygroundsResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update


class MockHomeViewModel : ViewModel() {

    private val _playgroundState: MutableStateFlow<DataState<PlaygroundsResponse>> =
        MutableStateFlow(DataState.Empty)
    val playgroundState: StateFlow<DataState<PlaygroundsResponse>> = _playgroundState

    private val _homeUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    lateinit var userData: LocalUser

//    init {
//        viewModelScope.launch {
//            localUserUseCases.getLocalUser().collect {
//                userData = it
//            }
//        }
//
//        viewModelScope.launch {
//            remoteUserUseCase.getPlaygroundsUseCase(
//                "Bearer ${userData.token}",
//                "7c6fa4dc-a314-4cbc-a4cc-5e6110020491"
//            ).collect {
//                _playgroundState.value = it
//            }
//        }
//    }

    fun onViewAllClick(playgroundCount: Int) {
        _homeUiState.update {
            it.copy(
//                playgroundCount = playgroundCount
            )
        }
    }
}