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

    fun getPlaygrounds() {
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

    fun onFavouriteClicked(playgroundId: Int) {
        if (_playgroundState.value is DataState.Success) {
            val playgrounds = (_playgroundState.value as DataState.Success).data.playgrounds
            val playground = playgrounds.find { it.id == playgroundId }
            if (playground != null) {
                _playgroundState.value = DataState.Success(
                    (_playgroundState.value as DataState.Success).data.copy(
                        playgrounds = playgrounds.map {
                            if (it.id == playgroundId) {
                                it.copy(isFavourite = !it.isFavourite)
                            } else {
                                it
                            }
                        }
                    )
                )
            }
        }

    }
}