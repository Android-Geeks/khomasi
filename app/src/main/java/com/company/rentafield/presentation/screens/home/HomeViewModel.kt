package com.company.rentafield.presentation.screens.home

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
    initialState = HomeReducer.initial(), reducer = HomeReducer()
) {

    fun getHomeData() {
        viewModelScope.launch(IO) {
            localUserUseCases.getLocalUser().collect { localUser ->
                sendEvent(HomeReducer.Event.UpdateLocalUser(localUser))
                launch { fetchPlaygrounds(localUser) }
                launch { fetchProfileImage(localUser) }
                launch { fetchUploadStatus(localUser) }
                launch { fetchUserData(localUser) }
            }
        }
    }

    private suspend fun fetchUserData(localUser: LocalUser) {
        remoteUserUseCase.userDataUseCase(
            "Bearer ${localUser.token}", localUser.userID ?: ""
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
//                            sendEvent(
//                                HomeReducer.Event.UpdateCoinsAndRating(
//                                    stat.data.coins,
//                                    stat.data.rating
//                                )
//                            )
                        }

                        is DataState.Error -> sendEffect(HomeReducer.Effect.Error.PlaygroundsError)

                        else -> Unit
                    }
                }
            }
        }
    }

    private suspend fun fetchPlaygrounds(localUser: LocalUser) {
        remoteUserUseCase.getPlaygroundsUseCase(
            "Bearer ${localUser.token}", localUser.userID ?: ""
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

    private suspend fun fetchUploadStatus(localUser: LocalUser) {
        aiUseCases.getUploadStatusUseCase(localUser.userID ?: "").collect { state ->
            sendEvent(HomeReducer.Event.UpdateCanUploadVideo(state is DataState.Success))
        }
    }

    private suspend fun fetchProfileImage(localUser: LocalUser) {
        remoteUserUseCase.getProfileImageUseCase(
            "Bearer ${localUser.token}", localUser.userID ?: ""
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