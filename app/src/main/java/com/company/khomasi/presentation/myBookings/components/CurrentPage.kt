package com.company.khomasi.presentation.myBookings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.company.khomasi.presentation.components.cards.BookingCard
import com.company.khomasi.presentation.components.cards.BookingStatus
import com.company.khomasi.presentation.myBookings.MyBookingUiState
@Composable
fun CurrentPage(
    uiState: MyBookingUiState,
    onClickPlaygroundCard: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp),
            color = MaterialTheme.colorScheme.background,
        ) {
            if (uiState.currentBookings.isNotEmpty()) {
                LazyColumn(
                    contentPadding = it,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    itemsIndexed(uiState.currentBookings) { index, it ->
                        when (uiState.page) {
                            1 -> {
                                BookingCard(
                                    bookingDetails = it,
                                    bookingStatus = BookingStatus.CONFIRMED,
                                    // bookingStatus = if (!uiState.currentBookings[index].isCanceled) BookingStatus.CONFIRMED else BookingStatus.CANCEL,
                                    onViewPlaygroundClick = {
                                        onClickPlaygroundCard(it.playgroundId)
                                    }
                                )
                                BookingCard(
                                    bookingDetails = it,
                                    bookingStatus = BookingStatus.CANCEL,
                                    onViewPlaygroundClick = {
                                        onClickPlaygroundCard(it.playgroundId)
                                    }
                                )
                            }

                            2 -> {
                                ConfirmationBottomSheet(
                                    bookingDetails = uiState.currentBookings[index],
                                    onBackClick = onBackClick
                                )
                            }

                            else -> {}
                        }
                    }
                }
            } else {
                EmptyScreen()
            }
        }
    }
}




