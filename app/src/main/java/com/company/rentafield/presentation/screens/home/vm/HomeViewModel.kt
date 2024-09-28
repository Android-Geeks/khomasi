package com.company.rentafield.presentation.screens.home.vm

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.LocalUser
import com.company.rentafield.domain.use_case.ai.AiUseCases
import com.company.rentafield.domain.use_case.local_user.LocalUserUseCases
import com.company.rentafield.domain.use_case.remote_user.RemoteUserUseCase
import com.company.rentafield.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val remoteUserUseCase: RemoteUserUseCase,
    private val localUserUseCases: LocalUserUseCases,
    private val aiUseCases: AiUseCases
) : BaseViewModel<HomeReducer.State, HomeReducer.Event, HomeReducer.Effect>(
    initialState = HomeReducer.initial(),
    reducer = HomeReducer()
) {

    init {
        getHomeData()
    }

    private fun getHomeData() {
        viewModelScope.launch(IO) {
            localUserUseCases.getLocalUser().collect { localUser ->
                if (localUser != LocalUser()) {
                    sendEvent(HomeReducer.Event.UpdateLocalUser(localUser))
                    launch { fetchPlaygrounds() }
                    launch { fetchProfileImage() }
                    launch { fetchUploadStatus() }
                }
            }
        }
    }

    private suspend fun fetchPlaygrounds() {
        remoteUserUseCase.getPlaygroundsUseCase(
            "Bearer ${state.value.localUser.token}",
            state.value.localUser.userID ?: ""
        ).collect { state ->
            when (state) {
                is DataState.Loading -> sendEvent(HomeReducer.Event.UpdateIsLoading(true))
                is DataState.Success -> {
                    sendEvent(
                        HomeReducer.Event.UpdatePlaygrounds(
                            state.data.playgrounds.sortedBy { it.distance }.take(3)
                        )
                    )
                    sendEvent(HomeReducer.Event.UpdateIsLoading(false))
                }

                else -> sendEvent(HomeReducer.Event.UpdateIsLoading(false))
            }
        }
    }

    private suspend fun fetchUploadStatus() {
        aiUseCases.getUploadStatusUseCase(state.value.localUser.userID ?: "")
            .collect { state ->
                sendEvent(HomeReducer.Event.UpdateCanUploadVideo(state is DataState.Success))
            }
    }

    private suspend fun fetchProfileImage() {
        remoteUserUseCase.getProfileImageUseCase(
            "Bearer ${state.value.localUser.token}",
            state.value.localUser.userID ?: ""
        ).collect { state ->
            if (state is DataState.Success) {
                Log.i("ProfileImage", "ProfileImage: ${state.data.profilePicture}")
                sendEvent(
                    HomeReducer.Event.UpdateProfileImage(state.data.profilePicture ?: "")
                )
            }
        }
    }
}