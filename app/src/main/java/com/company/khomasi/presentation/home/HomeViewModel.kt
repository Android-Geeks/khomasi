package com.company.khomasi.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.LocalUser
import com.company.khomasi.domain.model.PlaygroundsResponse
import com.company.khomasi.domain.use_case.local_user.LocalUserUseCases
import com.company.khomasi.domain.use_case.remote_user.RemoteUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class HomeViewModel  @Inject constructor(
    private val remoteUserUseCase: RemoteUserUseCase,
    private val localUserUseCases: LocalUserUseCases
) : ViewModel() {

    private val _homeState: MutableStateFlow<DataState<PlaygroundsResponse>> =
        MutableStateFlow(DataState.Empty)
    val homeState: StateFlow<DataState<PlaygroundsResponse>> = _homeState

    private lateinit var userData: LocalUser

    init {
        viewModelScope.launch {
            localUserUseCases.getLocalUser().collect {
                userData = it
            }
        }

        viewModelScope.launch {
            remoteUserUseCase.getPlaygroundsUseCase(
                "Bearer ${userData.token}",
                "7c6fa4dc-a314-4cbc-a4cc-5e6110020491"
            ).collect {
                _homeState.value = it
            }
        }
    }
}