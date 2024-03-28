package com.company.khomasi.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.FavouritePlaygroundResponse
import com.company.khomasi.domain.model.LocalUser
import com.company.khomasi.domain.use_case.remote_user.RemoteUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritePlaygroundsViewModel @Inject constructor(
    private val remoteUserUseCase: RemoteUserUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(FavouriteUiState())
    val uiState: StateFlow<FavouriteUiState> = _uiState.asStateFlow()

    private val _favouritePlaygroundsState =
        MutableStateFlow<DataState<FavouritePlaygroundResponse>>(DataState.Empty)
    val favouritePlaygroundsState: StateFlow<DataState<FavouritePlaygroundResponse>> =
        _favouritePlaygroundsState
    private var localUser = LocalUser()



    fun fetchUserFavoritePlaygrounds() {
        viewModelScope.launch {
            _favouritePlaygroundsState.value = DataState.Loading
            val token = localUser.token ?: ""
            val userId = localUser.userID ?: ""

            remoteUserUseCase.getUserFavoritePlaygroundsUseCase(
                token = token,
                userId = userId
            ).collect { dataState ->
                _favouritePlaygroundsState.value = dataState
            }
            _uiState.value = _uiState.value
        }
    }

    fun removeFromFavorites(playgroundId: String) {
        viewModelScope.launch {
            val token = localUser.token ?: ""
            val userId = localUser.userID ?: ""
            // val playgroundId = localUser.userID ?: ""
            remoteUserUseCase.deleteUserFavoriteUseCase("Bearer $token", userId, playgroundId)
            _uiState.value = _uiState.value.copy(isFavorite = false)

        }
    }
}
