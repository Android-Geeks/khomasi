package com.company.khomasi.presentation.favorite

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

    init {
        fetchUserFavoritePlaygrounds(localUser.token!!,localUser.userID!!)
    }

    fun fetchUserFavoritePlaygrounds(token: String,userId: String) {
        viewModelScope.launch {
            remoteUserUseCase.getUserFavoritePlaygroundsUseCase(
                token = token,
                userId = userId
            ).collect { dataState ->
                _favouritePlaygroundsState.value = dataState
            }

        }
    }

//    fun removeFromFavorites(playgroundId: String,token:String,userId:String) {
//            _uiState.value = _uiState.value.copy(isFavorite = false)
//        viewModelScope.launch {
//            remoteUserUseCase.deleteUserFavoriteUseCase("Bearer $token", userId, playgroundId)
//
//        }
//    }
}
