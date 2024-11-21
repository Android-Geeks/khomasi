package com.company.rentafield.presentation.screens.home

import androidx.annotation.StringRes
import com.company.rentafield.R
import com.company.rentafield.presentation.base.Reducer
import com.company.rentafield.presentation.screens.home.model.AdsContent
import com.company.rentafield.presentation.screens.home.model.adsList
import javax.annotation.concurrent.Immutable

class HomeReducer : Reducer<HomeReducer.State, HomeReducer.Event, HomeReducer.Effect> {

    @Immutable
    sealed class Event : Reducer.ViewEvent {
        data object BellClicked : Event()
        data object ViewAllClicked : Event()
        data object SearchBarClicked : Event()
        data object ImageProfileClicked : Event()
        data class AdClicked(val userId: String) : Event()
        data class FavouriteClick(val playgroundId: Int) : Event()
        data class BookNowClicked(val playgroundId: Int) : Event()
        data class UpdateIsLoading(val isLoading: Boolean) : Event()
        data class UpdateLocalUser(val localUser: com.company.rentafield.domain.models.LocalUser) :
            Event()

        data class UpdateAdList(val adList: List<AdsContent>) : Event()
        data class UpdateProfileImage(val profileImage: String) : Event()
        data class UpdateCanUploadVideo(val canUploadVideo: Boolean) : Event()
        data class UpdatePlaygrounds(val playgrounds: List<com.company.rentafield.domain.models.playground.Playground>) :
            Event()

        data class PlaygroundClick(val playgroundId: Int, val isFavourite: Boolean) : Event()
//        data class UpdateCoinsAndRating(val coins: Double, val rating: Double) : Event()
    }

    @Immutable
    sealed class Effect : Reducer.ViewEffect {
        data object NavigateToSearch : Effect()
        data object NavigateToProfile : Effect()
        data object NavigateToNotifications : Effect()
        data object NavigateToBrowsePlaygrounds : Effect()
        data class NavigateToAiService(val userId: String) : Effect()
        data class NavigateToPlaygroundDetails(
            val playgroundId: Int, val isFavourite: Boolean
        ) : Effect()

        sealed class Error(@StringRes val message: Int) : Effect() {
            data object UploadVideoError : Error(R.string.you_can_t_upload_video_now)
            data object ProfileImageError : Error(R.string.error_fetching_profile_image)
            data object PlaygroundsError : Error(R.string.error_fetching_playgrounds_data)
        }
    }

    @Immutable
    data class State(
        val isLoading: Boolean,
        val localUser: com.company.rentafield.domain.models.LocalUser,
        val profileImage: String,
        val canUploadVideo: Boolean,
        val adList: List<AdsContent>,
        val playgrounds: List<com.company.rentafield.domain.models.playground.Playground>
    ) : Reducer.ViewState

    override fun reduce(previousState: State, event: Event): Pair<State, Effect?> {

        return when (event) {

            is Event.UpdateAdList -> previousState.copy(adList = event.adList) to null

            is Event.UpdatePlaygrounds -> previousState.copy(playgrounds = event.playgrounds) to null

            is Event.UpdateCanUploadVideo ->
                previousState.copy(canUploadVideo = event.canUploadVideo) to
                        if (!event.canUploadVideo)
                            Effect.Error.UploadVideoError
                        else null

            is Event.UpdateProfileImage -> previousState.copy(profileImage = event.profileImage) to null

            is Event.UpdateLocalUser -> previousState.copy(localUser = event.localUser) to null

            is Event.UpdateIsLoading -> previousState.copy(isLoading = event.isLoading) to null

            is Event.ImageProfileClicked -> previousState to Effect.NavigateToProfile

            is Event.AdClicked -> previousState to Effect.NavigateToAiService(event.userId)

            is Event.FavouriteClick -> {
                val updatedPlaygrounds = previousState.playgrounds.map {
                    if (it.id == event.playgroundId) {
                        it.copy(isFavourite = !it.isFavourite)
                    } else it
                }
                previousState.copy(playgrounds = updatedPlaygrounds) to null
            }

            is Event.PlaygroundClick ->
                previousState to Effect.NavigateToPlaygroundDetails(
                    event.playgroundId,
                    event.isFavourite
                )

            is Event.BellClicked -> previousState to Effect.NavigateToNotifications

            is Event.SearchBarClicked -> previousState to Effect.NavigateToSearch

            is Event.ViewAllClicked -> previousState to Effect.NavigateToBrowsePlaygrounds

            is Event.BookNowClicked ->
                previousState to Effect.NavigateToPlaygroundDetails(event.playgroundId, false)
        }
    }

    companion object {
        fun initial() = State(
            adList = adsList,
            isLoading = false,
            profileImage = "",
            canUploadVideo = false,
            localUser = com.company.rentafield.domain.models.LocalUser(),
            playgrounds = emptyList()
        )
    }
}