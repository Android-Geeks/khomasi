package com.company.khomasi.presentation.components.cards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.model.LocalUser
import com.company.khomasi.domain.use_case.local_user.LocalUserUseCases
import com.company.khomasi.domain.use_case.remote_user.RemoteUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaygroundCardViewModel @Inject constructor(
    private val remoteUserUseCase: RemoteUserUseCase,
    private val localUserUseCases: LocalUserUseCases
) : ViewModel() {

    private val localUser = localUserUseCases.getLocalUser().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = LocalUser()
    )

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