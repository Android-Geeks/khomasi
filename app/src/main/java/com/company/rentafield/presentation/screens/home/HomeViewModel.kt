package com.company.rentafield.presentation.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.LocalUser
import com.company.rentafield.domain.model.UserDataResponse
import com.company.rentafield.domain.model.playground.PlaygroundsResponse
import com.company.rentafield.domain.use_case.ai.AiUseCases
import com.company.rentafield.domain.use_case.local_user.LocalUserUseCases
import com.company.rentafield.domain.use_case.remote_user.RemoteUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
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
    private val aiUseCases: AiUseCases
) : ViewModel() {

    private val _playgroundState: MutableStateFlow<DataState<PlaygroundsResponse>> =
        MutableStateFlow(DataState.Empty)
    val playgroundState: StateFlow<DataState<PlaygroundsResponse>> = _playgroundState

    private val _userDataState: MutableStateFlow<DataState<UserDataResponse>> =
        MutableStateFlow(DataState.Empty)

    private val _homeUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    private val _localUser = localUserUseCases.getLocalUser().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), LocalUser()
    )
    val localUser: StateFlow<LocalUser> = _localUser


    fun getHomeScreenData() {
        viewModelScope.launch(IO) {
            remoteUserUseCase.getPlaygroundsUseCase(
                token = "Bearer ${_localUser.value.token ?: ""}",
                userId = _localUser.value.userID ?: ""
            ).collect { playgroundsRes ->
                _playgroundState.value = playgroundsRes
            }
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
            aiUseCases.getUploadStatusUseCase(
                id = _localUser.value.userID ?: ""
            ).collect { uploadStatusRes ->
                _homeUiState.update {
                    it.copy(
                        canUploadVideo = uploadStatusRes is DataState.Success
                    )
                }
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

    fun getUserData() {
        viewModelScope.launch {
            remoteUserUseCase.userDataUseCase(
                token = "Bearer ${_localUser.value.token ?: ""}",
                userId = _localUser.value.userID ?: ""
            ).collect { dataState ->
                _userDataState.value = dataState
                Log.d("UserResponse", "UserResponse: $dataState")
                when (dataState) {
                    is DataState.Success -> {
                        localUserUseCases.saveLocalUser(
                            _localUser.value.copy(
                                coins = dataState.data.coins,
                                rating = dataState.data.rating
                            )
                        )
                    }
                    else -> {}
                }
            }
        }
    }
}