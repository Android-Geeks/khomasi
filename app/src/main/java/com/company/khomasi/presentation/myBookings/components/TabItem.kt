package com.company.khomasi.presentation.myBookings.components

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.company.khomasi.R
import com.company.khomasi.presentation.myBookings.MyBookingUiState
import kotlinx.coroutines.flow.StateFlow

sealed class TabItem(
    @StringRes val title: Int,
    val screens: @Composable (uiState: StateFlow<MyBookingUiState>) -> Unit
) {
    data object Current : TabItem(
        title = R.string.current,
        screens = { uiState -> CurrentPage(uiState) }
    )

    data object Expired : TabItem(
        title = R.string.expired,
        screens = { uiState -> ExpiredPage(uiState) }
    )
}

