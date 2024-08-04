package com.company.rentafield.presentation.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.LocalUser
import com.company.rentafield.domain.use_case.local_user.LocalUserUseCases
import com.company.rentafield.domain.use_case.remote_user.RemoteUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val remoteUserUseCase: RemoteUserUseCase,
    private val localUserUseCases: LocalUserUseCases
) : ViewModel() {

    private val _uiState: MutableStateFlow<FavouriteUiState> = MutableStateFlow(FavouriteUiState())
    val uiState: StateFlow<FavouriteUiState> = _uiState
    private var localUser = LocalUser()

    fun getFavoritePlaygrounds() {
        viewModelScope.launch {
            localUserUseCases.getLocalUser().collect {
                localUser = it
                remoteUserUseCase.getUserFavoritePlaygroundsUseCase(
                    "Bearer ${localUser.token}",
                    localUser.userID ?: ""
                ).collect { dataState ->
                    if (dataState is DataState.Success) {
                        _uiState.value = _uiState.value.copy(
                            playgrounds = dataState.data.playgrounds
                        )
                    }
                }
            }
        }
    }

    fun onFavouriteClicked(playgroundId: Int) {
        val updatedPlaygrounds = uiState.value.playgrounds.filterNot { it.id == playgroundId }
        _uiState.value = uiState.value.copy(playgrounds = updatedPlaygrounds)
    }
}


