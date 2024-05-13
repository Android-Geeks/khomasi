package com.company.khomasi.presentation.myBookings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.company.khomasi.domain.model.BookingDetails
import com.company.khomasi.presentation.components.cards.BookingCard
import com.company.khomasi.presentation.components.cards.BookingStatus
import com.company.khomasi.presentation.myBookings.MyBookingUiState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CurrentPage(
    uiState: StateFlow<MyBookingUiState>,
    onClickPlaygroundCard: (BookingDetails) -> Unit,
    onClickBookField: () -> Unit,
    cancelDetails: (Int, Boolean) -> Unit
) {
    val currentState = uiState.collectAsState().value
    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {}
        if (currentState.currentBookings.isNotEmpty()) {
            itemsIndexed(currentState.currentBookings) { index, it ->
                BookingCard(
                    bookingDetails = it,
                    bookingStatus = if (!currentState.currentBookings[index].isCanceled) BookingStatus.CONFIRMED else BookingStatus.CANCEL,
                    onViewPlaygroundClick = {
                        if (!currentState.currentBookings[index].isCanceled) {
                            onClickPlaygroundCard(it)
                        } else {
                            cancelDetails(it.playgroundId, it.isFavorite)
                        }
                    },
                    toRate = {},
                    reBook = {}
                )
            }
        } else {
            item {
                EmptyScreen(
                    onClickBookField = onClickBookField
                )
            }
        }
    }

}




