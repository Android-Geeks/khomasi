package com.company.rentafield.presentation.screens.favorite

import androidx.lifecycle.viewModelScope
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.usecases.localuser.LocalUserUseCases
import com.company.rentafield.domain.usecases.remoteuser.RemoteUserUseCases
import com.company.rentafield.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val remoteUserUseCases: RemoteUserUseCases,
    private val localUserUseCases: LocalUserUseCases
) : BaseViewModel<FavouriteReducer.State, FavouriteReducer.Event, FavouriteReducer.Effect>(
    initialState = FavouriteReducer.initial(),
    reducer = FavouriteReducer()
) {
    fun getFavoritePlaygrounds() {
        viewModelScope.launch {
            sendEvent(FavouriteReducer.Event.UpdateIsLoading(true))
            localUserUseCases.getLocalUser().collect { localUser ->
                remoteUserUseCases.getUserFavoritePlaygroundsUseCase(
                    "Bearer ${localUser.token}",
                    localUser.userID ?: ""
                ).collect { dataState ->
                    when (dataState) {
                        is DataState.Loading -> Unit
                        else -> {
                            sendEvent(FavouriteReducer.Event.UpdateIsLoading(false))
                            when (dataState) {
                                is DataState.Success ->
                                    sendEvent(
                                        FavouriteReducer.Event.UpdateFavouritePlaygrounds(
                                            dataState.data.playgrounds
                                        )
                                    )

                                is DataState.Error -> {
                                    when (dataState.code) {
                                        204 -> sendEvent(
                                            FavouriteReducer.Event.UpdateFavouritePlaygrounds(
                                                emptyList()
                                            )
                                        )

                                        else -> sendEffect(FavouriteReducer.Effect.Error())
                                    }
                                }

                                else -> Unit
                            }
                        }
                    }

                }
            }
        }
    }
}

