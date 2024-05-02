package com.company.khomasi.presentation.myBookings.components

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.company.khomasi.R
import com.company.khomasi.domain.model.BookingDetails
import com.company.khomasi.presentation.myBookings.MyBookingUiState
import kotlinx.coroutines.flow.StateFlow

sealed class TabItem(
    @StringRes val title: Int,
    val screens: @Composable (
        uiState: StateFlow<MyBookingUiState>,
        onClickPlaygroundCard: (BookingDetails) -> Unit,
        playgroundReview: () -> Unit,
        onCommentChange: (String) -> Unit,
        onRatingChange: (Float) -> Unit,
        reBook: (Int) -> Unit,
        onClickBookField: () -> Unit,
    ) -> Unit,
) {
    data object Current : TabItem(
        title = R.string.current,
        screens = { uiState, onClick, _, _, _, _, onClickBookField ->
            CurrentPage(uiState, onClick, onClickBookField)
        }
    )
    data object Expired : TabItem(
        title = R.string.expired,
        screens = { uiState, _, playgroundReview, onCommentChange, onRatingChange, reBook, onClickBookField ->
            ExpiredPage(
                uiState,
                playgroundReview,
                onCommentChange,
                onRatingChange,
                reBook,
                onClickBookField
            )
        }
    )
}

