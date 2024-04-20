package com.company.khomasi.presentation.myBookings.components

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.company.khomasi.R
import com.company.khomasi.presentation.myBookings.MyBookingUiState

sealed class TabItem(
    @StringRes val title: Int,
    val screens: @Composable (
        uiState: MyBookingUiState,
        onClickPlaygroundCard: ((Int) -> Unit)?,
        onBackClick: (() -> Unit)?
    ) -> Unit,
) {
    data object Current : TabItem(
        title = R.string.current,
        screens = { uiState, onClick, onBackClick ->
            CurrentPage(uiState, onClick ?: {}, onBackClick ?: {})
        }
    )
    data object Expired : TabItem(
        title = R.string.expired,
        screens = { uiState, _, _ ->
            ExpiredPage(uiState)
        }
    )
}

