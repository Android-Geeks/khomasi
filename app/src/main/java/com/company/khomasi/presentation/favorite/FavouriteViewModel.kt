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
class FavouriteViewModel @Inject constructor(
    private val remoteUserUseCase: RemoteUserUseCase
) : ViewModel() {
    private val _favState: MutableStateFlow<DataState<FavouritePlaygroundResponse>> =
        MutableStateFlow(DataState.Empty)
    val favState: StateFlow<DataState<FavouritePlaygroundResponse>> = _favState.asStateFlow()
    private var localUser = LocalUser()

    init {
        getFavoritePlaygrounds(localUser.token!!, localUser.userID!!)
    }


    private fun getFavoritePlaygrounds(token: String, userId: String) {
        viewModelScope.launch {
            remoteUserUseCase.getUserFavoritePlaygroundsUseCase(token, userId).collect {dataState ->
                _favState.value = dataState
            }
        }
    }
}
