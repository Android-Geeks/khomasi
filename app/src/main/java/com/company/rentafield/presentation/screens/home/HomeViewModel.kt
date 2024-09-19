package com.company.rentafield.presentation.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.LocalUser
import com.company.rentafield.domain.model.UserDataResponse
import com.company.rentafield.domain.use_case.ai.AiUseCases
import com.company.rentafield.domain.use_case.local_user.LocalUserUseCases
import com.company.rentafield.domain.use_case.remote_user.RemoteUserUseCase
import com.company.rentafield.presentation.screens.home.model.HomeUiState
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
    private val aiUseCases: AiUseCases
) : ViewModel() {
    private val _userDataState: MutableStateFlow<DataState<UserDataResponse>> =
        MutableStateFlow(DataState.Empty)

    private val _homeUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    private val _localUser = localUserUseCases.getLocalUser().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), LocalUser()
    )

    init {
        viewModelScope.launch {
            _localUser.collect { localUser ->
                _homeUiState.value = _homeUiState.value.copy(
                    userId = localUser.userID ?: "",
                    userName = localUser.firstName ?: ""
                )
                launch { getProfileImage(localUser.userID) }
                launch { getUploadStatus(localUser.userID) }
                launch { getUserData(localUser.userID, localUser.token) }
                launch { getPlaygrounds(localUser.userID, localUser.token) }
            }
        }
    }

    private suspend fun getPlaygrounds(
        userID: String? = _localUser.value.userID,
        userToken: String? = _localUser.value.token
    ) {
        remoteUserUseCase.getPlaygroundsUseCase(
            token = "Bearer ${userToken ?: ""}",
            userId = userID ?: ""
        ).collect { playgroundsRes ->
            if (playgroundsRes is DataState.Success) {
                _homeUiState.value =
                    _homeUiState.value.copy(playgrounds = playgroundsRes.data.playgrounds)
            }
        }
    }

    private suspend fun getProfileImage(
        userID: String? = _localUser.value.userID,
        userToken: String? = _localUser.value.token
    ) {
        remoteUserUseCase.getProfileImageUseCase(
            token = "Bearer ${userToken ?: ""}",
            userId = userID ?: ""
        ).collect { profileImageRes ->
            if (profileImageRes is DataState.Success) {
                _homeUiState.value =
                    _homeUiState.value.copy(profileImage = profileImageRes.data.profilePicture)
            }
        }
    }

    private suspend fun getUploadStatus(
        userID: String? = _localUser.value.userID
    ) {
        aiUseCases.getUploadStatusUseCase(
            id = userID ?: ""
        ).collect { uploadStatusRes ->
            _homeUiState.update {
                it.copy(
                    canUploadVideo = uploadStatusRes is DataState.Success
                )
            }
        }
    }

    fun onFavouriteClicked(playgroundId: Int) {
        val updatedPlaygrounds=_homeUiState.value.playgrounds.map {
            if (it.id == playgroundId) {
                it.copy(isFavourite = !it.isFavourite)
            } else it
        }
        _homeUiState.value=_homeUiState.value.copy(playgrounds = updatedPlaygrounds)
    }

    private suspend fun getUserData(
        userID: String? = _localUser.value.userID,
        userToken: String? = _localUser.value.token
    ) {
        remoteUserUseCase.userDataUseCase(
            token = "Bearer ${userToken ?: ""}",
            userId = userID ?: ""
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
