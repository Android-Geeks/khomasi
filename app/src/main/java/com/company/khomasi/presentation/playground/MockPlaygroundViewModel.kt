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
//    init {
//        viewModelScope.launch {
//            remoteUserUseCase.getSpecificPlaygroundUseCase(
//                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoidXNlMjJyQGV4YW1wbGUuY29tIiwiaHR0cDovL3NjaGVtYXMubWljcm9zb2Z0LmNvbS93cy8yMDA4LzA2L2lkZW50aXR5L2NsYWltcy9yb2xlIjoiVXNlciIsImV4cCI6MTcxMzEwMDIxMSwiaXNzIjoiV2ViQVBJRGVtbyIsImF1ZCI6IldlYkFQSURlbW8ifQ.uEZAWVuUrDjRk3neC6nhA0AgCoQvQqtC2WiQ1NYclto",
//                1
//            ).collect {
//                _playgroundState.value = it
//            }
//        }
//    }

    suspend fun getPlaygroundDetails() {
        // localUserUseCases.getLocalUser().collect { localUser ->
        //     localUserUseCases.getPlaygroundId().collect { id ->
        //         remoteUserUseCase.getSpecificPlaygroundUseCase(
        //             token = "Bearer ${localUser.token}",
        //             id = id
        //         ).collect {
        //             _playgroundState.value = it
        //         }
        //     }
        // }
    }
}
