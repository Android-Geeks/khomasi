package com.company.khomasi.presentation.playground

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.LocalUser
import com.company.khomasi.domain.model.PlaygroundScreenResponse
import com.company.khomasi.domain.use_case.local_user.LocalUserUseCases
import com.company.khomasi.domain.use_case.remote_user.RemoteUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaygroundViewModel @Inject constructor(
    private val remoteUserUseCase: RemoteUserUseCase,
    private val localUserUseCases: LocalUserUseCases
) : ViewModel() {

    private val _playgroundId = localUserUseCases.getPlaygroundId().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 1
    )

    private val localUser = localUserUseCases.getLocalUser().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = LocalUser()
    )

    private val _playgroundState: MutableStateFlow<DataState<PlaygroundScreenResponse>> =
        MutableStateFlow(DataState.Empty)
    val playgroundState: StateFlow<DataState<PlaygroundScreenResponse>> = _playgroundState

    private val _uiState: MutableStateFlow<PlaygroundUiState> =
        MutableStateFlow(PlaygroundUiState())
    val uiState: StateFlow<PlaygroundUiState> = _uiState


    fun getPlaygroundDetails() {
        Log.d("PlaygroundViewModel", "getPlaygroundDetails: ${localUser.value.token} ")
        viewModelScope.launch {
            remoteUserUseCase.getSpecificPlaygroundUseCase(
                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiYWxAZ21haWwuY29tIiwiaHR0cDovL3NjaGVtYXMubWljcm9zb2Z0LmNvbS93cy8yMDA4LzA2L2lkZW50aXR5L2NsYWltcy9yb2xlIjoiVXNlciIsImV4cCI6MTcxMzI0NTc5OCwiaXNzIjoiV2ViQVBJRGVtbyIsImF1ZCI6IldlYkFQSURlbW8ifQ.P67CcDaxVi1qExuOClkxJlPImn2Gk0ImyEjy_PA8XHk",
                _playgroundId.value
            ).collect {
                _playgroundState.value = it
            }
        }
    }

//    init {
//        viewModelScope.launch {
//            remoteUserUseCase.getSpecificPlaygroundUseCase(
//                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoidXNlMjJyQGV4YW1wbGUuY29tIiwiaHR0cDovL3NjaGVtYXMubWljcm9zb2Z0LmNvbS93cy8yMDA4LzA2L2lkZW50aXR5L2NsYWltcy9yb2xlIjoiVXNlciIsImV4cCI6MTcxMzEwMDIxMSwiaXNzIjoiV2ViQVBJRGVtbyIsImF1ZCI6IldlYkFQSURlbW8ifQ.uEZAWVuUrDjRk3neC6nhA0AgCoQvQqtC2WiQ1NYclto",
//                1     // will change this to dynamic value coming from home screen
//            ).collect {
//                _playgroundState.value = it
//            }
//        }
//    }

    fun onClickFavourite() {
        _uiState.value = _uiState.value.copy(isFavourite = !_uiState.value.isFavourite)
    }
}