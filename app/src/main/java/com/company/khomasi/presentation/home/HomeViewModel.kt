package com.company.khomasi.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.PlaygroundsResponse
import com.company.khomasi.domain.use_case.local_user.LocalUserUseCases
import com.company.khomasi.domain.use_case.remote_user.RemoteUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val remoteUserUseCase: RemoteUserUseCase,
    private val localUserUseCases: LocalUserUseCases
) : ViewModel() {

    private val _playgroundState: MutableStateFlow<DataState<PlaygroundsResponse>> =
        MutableStateFlow(DataState.Empty)
    val playgroundState: StateFlow<DataState<PlaygroundsResponse>> = _playgroundState

    private val _homeUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    init {
        viewModelScope.launch {
            localUserUseCases.getLocalUser().collect { userData ->

                remoteUserUseCase.getPlaygroundsUseCase(
                    token = "Bearer ${userData.token}",
                    userId = userData.userID ?: ""
                ).collect { playgroundsRes ->
                    _playgroundState.value = playgroundsRes
                }
                _homeUiState.value = HomeUiState(
                    name = userData.firstName ?: "",
                    userImg = userData.profilePicture
                )
            }
        }
    }

//userId : "7c6fa4dc-a314-4cbc-a4cc-5e6110020491"

    fun onClickViewAll() {
        _homeUiState.update {
            it.copy(
                viewAllSwitch = true
            )
        }
    }

    fun onClickPlayground(playgroundId: Int) {
        viewModelScope.launch {
            localUserUseCases.savePlaygroundId(playgroundId)
        }
    }
}