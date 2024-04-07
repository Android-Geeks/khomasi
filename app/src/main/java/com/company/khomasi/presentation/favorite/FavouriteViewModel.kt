package com.company.khomasi.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.FavouritePlaygroundResponse
import com.company.khomasi.domain.model.LocalUser
import com.company.khomasi.domain.use_case.local_user.LocalUserUseCases
import com.company.khomasi.domain.use_case.remote_user.RemoteUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val remoteUserUseCase: RemoteUserUseCase,
    private val localUserUseCases: LocalUserUseCases
) : ViewModel() {
    private val _favState: MutableStateFlow<DataState<FavouritePlaygroundResponse>> =
        MutableStateFlow(DataState.Empty)
    val favState: StateFlow<DataState<FavouritePlaygroundResponse>> = _favState.asStateFlow()
    private val _uiState: MutableStateFlow<FavouriteUiState> = MutableStateFlow(FavouriteUiState())
    val uiState: StateFlow<FavouriteUiState> = _uiState
    private var localUser = LocalUser()

    init {
        getFavoritePlaygrounds()
    }

    private fun getFavoritePlaygrounds() {
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
}

