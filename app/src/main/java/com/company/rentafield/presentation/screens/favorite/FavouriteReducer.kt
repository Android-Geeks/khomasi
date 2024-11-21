package com.company.rentafield.presentation.screens.favorite

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.company.rentafield.R
import com.company.rentafield.presentation.base.Reducer

class FavouriteReducer :
    Reducer<FavouriteReducer.State, FavouriteReducer.Event, FavouriteReducer.Effect> {

    @Immutable
    sealed class Event : Reducer.ViewEvent {
        data object BackClicked : Event()
        data class FavouriteClick(val playgroundId: Int) : Event()
        data class UpdateIsLoading(val isLoading: Boolean) : Event()
        data class PlaygroundClick(val playgroundId: Int, val isFavourite: Boolean) : Event()
        data class UpdateFavouritePlaygrounds(val favouritePlaygrounds: List<com.company.rentafield.domain.models.playground.Playground>) :
            Event()
    }

    @Immutable
    sealed class Effect : Reducer.ViewEffect {
        data object NavigateBack : Effect()
        data class NavigateToPlaygroundDetails(
            val playgroundId: Int, val isFavourite: Boolean
        ) : Effect()

        data class Error(@StringRes val message: Int = (R.string.error_fetching_playgrounds_data)) :
            Effect()
    }

    @Immutable
    data class State(
        val userID: String,
        val userToken: String,
        val isLoading: Boolean,
        val favouritePlaygrounds: List<com.company.rentafield.domain.models.playground.Playground>
    ) : Reducer.ViewState


    override fun reduce(previousState: State, event: Event): Pair<State, Effect?> {
        return when (event) {
            is Event.FavouriteClick -> {
                val updatedPlaygrounds =
                    previousState.favouritePlaygrounds.filterNot { it.id == event.playgroundId }
                previousState.copy(favouritePlaygrounds = updatedPlaygrounds) to null
            }

            is Event.UpdateFavouritePlaygrounds -> previousState.copy(favouritePlaygrounds = event.favouritePlaygrounds) to null

            is Event.PlaygroundClick ->
                previousState to Effect.NavigateToPlaygroundDetails(
                    event.playgroundId,
                    event.isFavourite
                )

            is Event.UpdateIsLoading -> previousState.copy(isLoading = event.isLoading) to null

            is Event.BackClicked -> previousState to Effect.NavigateBack
        }
    }

    companion object {
        fun initial() = State(
            userID = "",
            userToken = "",
            isLoading = false,
            favouritePlaygrounds = emptyList()
        )
    }
}