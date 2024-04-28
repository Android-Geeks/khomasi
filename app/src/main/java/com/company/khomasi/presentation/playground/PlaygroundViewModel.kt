package com.company.khomasi.presentation.playground

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.PlaygroundReviewResponse
import com.company.khomasi.domain.model.PlaygroundScreenResponse
import com.company.khomasi.domain.use_case.local_user.LocalUserUseCases
import com.company.khomasi.domain.use_case.remote_user.RemotePlaygroundUseCase
import com.company.khomasi.domain.use_case.remote_user.RemoteUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaygroundViewModel @Inject constructor(
    private val remoteUserUseCase: RemoteUserUseCase,
    private val remotePlaygroundUseCase: RemotePlaygroundUseCase,
    private val localUserUseCases: LocalUserUseCases
) : ViewModel() {

    private val _playgroundState: MutableStateFlow<DataState<PlaygroundScreenResponse>> =
        MutableStateFlow(DataState.Empty)
    val playgroundState: StateFlow<DataState<PlaygroundScreenResponse>> = _playgroundState

    private val _uiState: MutableStateFlow<PlaygroundUiState> =
        MutableStateFlow(PlaygroundUiState())
    val uiState: StateFlow<PlaygroundUiState> = _uiState

    private val _reviewsState: MutableStateFlow<DataState<PlaygroundReviewResponse>> =
        MutableStateFlow(DataState.Empty)
    val reviewsState: StateFlow<DataState<PlaygroundReviewResponse>> = _reviewsState

    fun getPlaygroundDetails() {
        viewModelScope.launch {
            localUserUseCases.getLocalUser().collect { localUser ->
                localUserUseCases.getPlaygroundId().collect { id ->
                    remoteUserUseCase.getSpecificPlaygroundUseCase(
                        token = "Bearer ${localUser.token}",
                        id = id
                    ).collect {
                        _playgroundState.value = it
                    }
                }
            }
        }
    }

    fun getPlaygroundReviews() {
        viewModelScope.launch {
            localUserUseCases.getLocalUser().collect { localUser ->
                localUserUseCases.getPlaygroundId().collect { id ->
                    remotePlaygroundUseCase.getPlaygroundReviewsUseCase(
                        token = "Bearer ${localUser.token}",
                        id = 1
                    ).collect {
                        _reviewsState.value = it
                    }
                }
            }
        }
    }


    fun onClickFavourite() {
        _uiState.value = _uiState.value.copy(isFavourite = !_uiState.value.isFavourite)
    }
}