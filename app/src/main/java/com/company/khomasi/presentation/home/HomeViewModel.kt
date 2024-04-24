package com.company.khomasi.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.LocalUser
import com.company.khomasi.domain.model.PlaygroundsResponse
import com.company.khomasi.domain.use_case.local_user.LocalPlaygroundUseCase
import com.company.khomasi.domain.use_case.local_user.LocalUserUseCases
import com.company.khomasi.domain.use_case.remote_user.RemoteUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val remoteUserUseCase: RemoteUserUseCase,
    private val localUserUseCases: LocalUserUseCases,
    private val localPlaygroundUseCase: LocalPlaygroundUseCase
) : ViewModel() {

    private val _playgroundState: MutableStateFlow<DataState<PlaygroundsResponse>> =
        MutableStateFlow(DataState.Empty)
    val playgroundState: StateFlow<DataState<PlaygroundsResponse>> = _playgroundState

    private val _homeUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    private val _localUser = localUserUseCases.getLocalUser().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), LocalUser()
    )
    val localUser: StateFlow<LocalUser> = _localUser


    fun getHomeScreenData() {
        viewModelScope.launch {
            remoteUserUseCase.getProfileImageUseCase(
                token = "Bearer ${_localUser.value.token ?: ""}",
                userId = _localUser.value.userID ?: ""
            ).collect { profileImageRes ->
                if (profileImageRes is DataState.Success) {
                    _homeUiState.update {
                        it.copy(
                            profileImage = profileImageRes.data.profilePicture
                        )
                    }
                }
            }
            remoteUserUseCase.getPlaygroundsUseCase(
                token = "Bearer ${_localUser.value.token ?: ""}",
                userId = _localUser.value.userID ?: ""
            ).collect { playgroundsRes ->
                _playgroundState.value = playgroundsRes
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

    fun onClickPlayground(playgroundId: Int, playgroundName: String, playgroundPrice: Int) {
        viewModelScope.launch {
            localUserUseCases.savePlaygroundId(playgroundId)
            getPlaygroundData(playgroundName, playgroundPrice)
        }
    }

    // --------    Until locate playground id into LocalPlaygroundUseCases -------------
    private fun getPlaygroundData(playgroundName: String, playgroundPrice: Int) {
        viewModelScope.launch {
            localPlaygroundUseCase.apply {
                this.savePlaygroundName(playgroundName)
                this.savePlaygroundPrice(playgroundPrice)
            }
        }
    }

    fun onFavouriteClicked(playgroundId: Int) {
        if (_playgroundState.value is DataState.Success) {
            val playgrounds = (_playgroundState.value as DataState.Success).data.playgrounds
            val playground = playgrounds.find { it.id == playgroundId }
            if (playground != null) {
                _playgroundState.value =
                    DataState.Success(
                        (_playgroundState.value as DataState.Success).data.copy(
                            playgrounds = playgrounds.map {
                                if (it.id == playgroundId) {
                                    it.copy(isFavourite = !it.isFavourite)
                                } else {
                                    it
                                }
                            })
                    )
            }
        }
    }
}