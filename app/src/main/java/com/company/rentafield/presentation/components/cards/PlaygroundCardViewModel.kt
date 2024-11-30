package com.company.rentafield.presentation.components.cards

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.rentafield.domain.usecases.localuser.LocalUserUseCases
import com.company.rentafield.domain.usecases.remoteuser.RemoteUserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaygroundCardViewModel @Inject constructor(
    private val remoteUserUseCases: RemoteUserUseCases,
    private val localUserUseCases: LocalUserUseCases
) : ViewModel() {


    fun updateUserFavourite(playgroundId: Int, isFavourite: Boolean) {
        viewModelScope.launch {
            localUserUseCases.getLocalUser().collect { localUser ->
                Log.d("PlaygroundCardViewModel", "updateUserFavourite: $isFavourite")
                if (isFavourite) {
                    remoteUserUseCases.deleteUserFavoriteUseCase(
                        token = "Bearer ${localUser.token ?: ""}",
                        userId = localUser.userID ?: "",
                        playgroundId = playgroundId,
                    ).collect {
                        Log.d("PlaygroundCardViewModel", "updateUserFavourite: $it")
                    }
                } else {
                    remoteUserUseCases.userFavouriteUseCase(
                        token = "Bearer ${localUser.token ?: ""}",
                        userId = localUser.userID ?: "",
                        playgroundId = playgroundId,
                    ).collect {
                        Log.d("PlaygroundCardViewModel", "updateUserFavourite: $it")
                    }
                }
            }
        }
    }
}