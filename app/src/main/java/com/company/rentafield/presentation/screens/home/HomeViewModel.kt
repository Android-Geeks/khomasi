package com.company.rentafield.presentation.screens.home

import androidx.lifecycle.viewModelScope
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.usecases.ai.AiUseCases
import com.company.rentafield.domain.usecases.localuser.LocalUserUseCases
import com.company.rentafield.domain.usecases.remoteuser.RemoteUserUseCase
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
    initialState = HomeReducer.initial(), reducer = HomeReducer()
) {

    fun getHomeData() {
        viewModelScope.launch(IO) {
            localUserUseCases.getLocalUser().collect { localUser ->
                sendEvent(HomeReducer.Event.UpdateLocalUser(localUser))
                launch(IO) { fetchPlaygrounds() }
                launch(IO) { fetchProfileImage() }
                launch(IO) { fetchUploadStatus() }
                launch(IO) { fetchUserData() }
            }
        }
    }

    private suspend fun fetchUserData() {
        remoteUserUseCase.userDataUseCase(
            "Bearer ${state.value.localUser.token}", state.value.localUser.userID ?: ""
        ).collect { stat ->
            when (stat) {
                is DataState.Loading -> {
                    if (super.state.value.playgrounds.isEmpty()) sendEvent(
                        HomeReducer.Event.UpdateIsLoading(
                            true
                        )
                    )
                }

                else -> {
                    sendEvent(HomeReducer.Event.UpdateIsLoading(false))
                    when (stat) {
                        is DataState.Success -> {
                            localUserUseCases.saveLocalUser(
                                localUser = state.value.localUser.copy(
                                    coins = stat.data.coins, rating = stat.data.rating
                                )
                            )
                        }

                        is DataState.Error -> Unit

                        else -> Unit
                    }
                }
            }
        }
    }

    private suspend fun fetchPlaygrounds() {
        remoteUserUseCase.getPlaygroundsUseCase(
            "Bearer ${state.value.localUser.token}", state.value.localUser.userID ?: ""
        ).collect { state ->
            when (state) {
                is DataState.Loading -> {
                    if (super.state.value.playgrounds.isEmpty()) sendEvent(
                        HomeReducer.Event.UpdateIsLoading(
                            true
                        )
                    )
                }

                else -> {
                    sendEvent(HomeReducer.Event.UpdateIsLoading(false))
                    when (state) {
                        is DataState.Success -> {
                            sendEvent(
                                HomeReducer.Event.UpdatePlaygrounds(
                                    state.data.playgrounds.sortedBy { it.distance }.take(3)
                                )
                            )
                        }

                        is DataState.Error -> sendEffect(HomeReducer.Effect.Error.PlaygroundsError)

                        else -> Unit
                    }
                }
            }
        }
    }

    private suspend fun fetchUploadStatus() {
        aiUseCases.getUploadStatusUseCase(state.value.localUser.userID ?: "").collect { state ->
            sendEvent(HomeReducer.Event.UpdateCanUploadVideo(state is DataState.Success))
        }
    }

    private suspend fun fetchProfileImage() {
        remoteUserUseCase.getProfileImageUseCase(
            "Bearer ${state.value.localUser.token}", state.value.localUser.userID ?: ""
        ).collect { state ->
            when (state) {
                is DataState.Success -> sendEvent(
                    HomeReducer.Event.UpdateProfileImage(state.data.profilePicture ?: "")
                )

                is DataState.Error -> sendEffect(HomeReducer.Effect.Error.ProfileImageError)

                else -> Unit
            }
        }
    }
}