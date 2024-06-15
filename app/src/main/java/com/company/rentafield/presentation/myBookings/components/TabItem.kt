package com.company.rentafield.presentation.myBookings.components

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.company.rentafield.R
import com.company.rentafield.domain.model.BookingDetails
import com.company.rentafield.presentation.myBookings.MyBookingUiState
import kotlinx.coroutines.flow.StateFlow

sealed class TabItem(
    @StringRes val title: Int,
    val screens: @Composable (
        uiState: StateFlow<MyBookingUiState>,
        onClickPlaygroundCard: (BookingDetails) -> Unit,
        playgroundReview: () -> Unit,
        onCommentChange: (String) -> Unit,
        onRatingChange: (Float) -> Unit,
        reBook: (Int, Boolean) -> Unit,
        onClickBookField: () -> Unit,
        cancelDetails: (Int, Boolean) -> Unit,
        toRate: (Int) -> Unit,
    ) -> Unit,
) {
    data object Current : TabItem(
        title = R.string.current,
        screens = { uiState, onClick, _, _, _, _, onClickBookField, cancelDetails, _ ->
            CurrentPage(uiState, onClick, onClickBookField, cancelDetails)
        }
    )

    data object Expired : TabItem(
        title = R.string.expired,
        screens = { uiState, _, playgroundReview, onCommentChange, onRatingChange, reBook, onClickBookField, _, toRate ->
            ExpiredPage(
                uiState,
                playgroundReview,
                onCommentChange,
                onRatingChange,
                reBook,
                onClickBookField,
                toRate
            )
        }
    )
}

