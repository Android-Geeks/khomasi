package com.company.khomasi.presentation.components.cards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.model.LocalUser
import com.company.khomasi.domain.model.Playground
import com.company.khomasi.domain.use_case.local_user.LocalUserUseCases
import com.company.khomasi.domain.use_case.remote_user.RemoteUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaygroundCardViewModel @Inject constructor(
    private val remoteUserUseCase: RemoteUserUseCase,
    private val localUserUseCases: LocalUserUseCases
) : ViewModel() {
    private val _playgrounds = MutableStateFlow<List<Playground>>(emptyList())
    val playgrounds = _playgrounds.asStateFlow()

    private val _uiState = MutableStateFlow(
        PlaygroundCardState(
            Playground(
                id = 1,
                name = "Playground Name",
                address = "Address",
                rating = 4.5,
                feesForHour = 100,
                isBookable = true,
                distance = 5.0,
                playgroundPicture = null,
                isFavourite = false
            ),
            isFavorite = false
        )

    )
    val uiState = _uiState.asStateFlow()

    private val localUser = localUserUseCases.getLocalUser().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = LocalUser()
    )

//    fun userFavourite(playgroundId: String) {
//        viewModelScope.launch {
//            localUserUseCases.getLocalUser().collect {
//                localUser = it
//                remoteUserUseCase.userFavouriteUseCase(
//                    "Bearer ${localUser.token}",
//                    localUser.userID ?: "",
//                    playgroundId
//                ).collect {
//                    _playgrounds.value = _playgrounds.value.map { playground ->
//                        if (playground.id.toString() == playgroundId) {
//                            playground.copy(isFavourite = true)
//                        } else {
//                            playground
//                        }
//                    }
//                }
//
//            }
//        }
//    }
//
//    fun deleteUserFavourite(playgroundId: String) {
//        viewModelScope.launch {
//            localUserUseCases.getLocalUser().collect {
//                localUser = it
//                remoteUserUseCase.deleteUserFavoriteUseCase(
//                    "Bearer ${localUser.token}",
//                    localUser.userID ?: "",
//                    playgroundId
//                ).collect() {
//                    _playgrounds.value = _playgrounds.value.map { playground ->
//                        if (playground.id.toString() == playgroundId) {
//                            playground.copy(isFavourite = false)
//                        } else {
//                            playground
//                        }
//                    }
//                }
//            }
//        }
//    }

    fun updateUiState(uiState: Playground, favourite: Boolean) {
        _uiState.value = PlaygroundCardState(
            uiState, favourite
        )
    }

    fun updateFavorite(isFavorite: Boolean) {
        _uiState.value = _uiState.value.copy(
            isFavorite = isFavorite
        )
    }

    fun updateUserFavourite(playgroundId: String, isFavourite: Boolean) {
        viewModelScope.launch {
            if (isFavourite) {
                remoteUserUseCase.userFavouriteUseCase(
                    token = localUser.value.token ?: "",
                    userId = localUser.value.userID ?: "",
                    playgroundId = playgroundId,
                )
            } else {
                remoteUserUseCase.deleteUserFavoriteUseCase(
                    token = localUser.value.token ?: "",
                    userId = localUser.value.userID ?: "",
                    playgroundId = playgroundId,
                )
            }
        }
    }
}