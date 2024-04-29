package com.company.khomasi.presentation.myBookings.components

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.company.khomasi.R
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.PlaygroundReviewResponse
import com.company.khomasi.presentation.myBookings.MyBookingUiState
import kotlinx.coroutines.flow.StateFlow

sealed class TabItem(
    @StringRes val title: Int,
    val screens: @Composable (
        uiState: StateFlow<MyBookingUiState>,
        onClickPlaygroundCard: (Int) -> Unit,
        playgroundReview: (() -> Unit)?,
        responseState: StateFlow<DataState<PlaygroundReviewResponse>>?,
        onCommentChange: ((String) -> Unit)?,
        onRatingChange: ((Float) -> Unit)?,
        //  myBooking: StateFlow<DataState<BookingDetails>>?,

    ) -> Unit,
) {
    data object Current : TabItem(
        title = R.string.current,
        screens = { uiState, onClick, _, _, _, _ ->
            CurrentPage(uiState, onClick)
        }
    )
    data object Expired : TabItem(
        title = R.string.expired,
        screens = { uiState, onClick, playgroundReview, reviewResponse, onCommentChange, onRatingChange ->
            ExpiredPage(
                uiState,
                playgroundReview!!,
                onClick,
                reviewResponse!!,
                onCommentChange!!,
                onRatingChange!!,
                //myBooking!!
            )
        }
    )
}

