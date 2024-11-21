package com.company.rentafield.presentation.screens.playground

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.usecases.localuser.LocalUserUseCases
import com.company.rentafield.domain.usecases.remoteuser.RemotePlaygroundUseCase
import com.company.rentafield.domain.usecases.remoteuser.RemoteUserUseCase
import com.company.rentafield.presentation.screens.playground.model.PlaygroundInfoUiState
import com.company.rentafield.presentation.screens.playground.model.PlaygroundReviewsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaygroundViewModel @Inject constructor(
    private val remoteUserUseCase: RemoteUserUseCase,
    private val remotePlaygroundUseCase: RemotePlaygroundUseCase,
    private val localUserUseCases: LocalUserUseCases,
) : ViewModel() {

    private val _playgroundState: MutableStateFlow<DataState<com.company.rentafield.domain.models.playground.PlaygroundScreenResponse>> =
        MutableStateFlow(DataState.Empty)
    val playgroundState: StateFlow<DataState<com.company.rentafield.domain.models.playground.PlaygroundScreenResponse>> =
        _playgroundState

    private val _infoUiState: MutableStateFlow<PlaygroundInfoUiState> =
        MutableStateFlow(PlaygroundInfoUiState())
    val infoUiState: StateFlow<PlaygroundInfoUiState> = _infoUiState

    private val _reviewsUiState: MutableStateFlow<PlaygroundReviewsUiState> =
        MutableStateFlow(PlaygroundReviewsUiState())
    val reviewsUiState: StateFlow<PlaygroundReviewsUiState> = _reviewsUiState

    private val _reviewsState: MutableStateFlow<DataState<com.company.rentafield.domain.models.playground.PlaygroundReviewsResponse>> =
        MutableStateFlow(DataState.Empty)
    val reviewsState: StateFlow<DataState<com.company.rentafield.domain.models.playground.PlaygroundReviewsResponse>> =
        _reviewsState


    fun getPlaygroundDetails(playgroundId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val localUser = localUserUseCases.getLocalUser().first()

            remoteUserUseCase.getSpecificPlaygroundUseCase(
                token = "Bearer ${localUser.token}", id = playgroundId
            ).collect { playgroundRes ->
                _playgroundState.value = playgroundRes

            }

            remotePlaygroundUseCase.getPlaygroundReviewsUseCase(
                token = "Bearer ${localUser.token}", id = playgroundId
            ).collect { reviewRes ->
                _reviewsState.value = reviewRes
                if (reviewRes is DataState.Success) {
                    _reviewsUiState.update {
                        it.copy(reviewsCount = reviewRes.data.reviewList.size)
                    }
                }
            }
        }
    }

    fun updateFavouriteAndPlaygroundId(
        isFavourite: Boolean, playgroundId: Int
    ) {
        _infoUiState.update {
            it.copy(
                isFavourite = isFavourite, playgroundId = playgroundId
            )
        }
    }

    fun updateShowReviews() {
        _reviewsUiState.update {
            it.copy(showReviews = !_reviewsUiState.value.showReviews)
        }
    }
}




