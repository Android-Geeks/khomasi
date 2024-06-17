package com.company.rentafield.presentation.myBookings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.rentafield.domain.model.BookingDetails
import com.company.rentafield.presentation.components.cards.BookingCard
import com.company.rentafield.presentation.components.cards.BookingStatus
import com.company.rentafield.presentation.myBookings.MyBookingUiState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CurrentPage(
    uiState: StateFlow<MyBookingUiState>,
    onClickPlaygroundCard: (BookingDetails) -> Unit,
    onClickBookField: () -> Unit,
    cancelDetails: (Int, Boolean) -> Unit
) {
    val currentState by uiState.collectAsStateWithLifecycle()
    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {}
        itemsIndexed(currentState.currentBookings) { index, it ->
            if (currentState.currentBookings.isNotEmpty()) {
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
            } else {
                EmptyScreen(
                    onClickBookField = onClickBookField
                )
            }
        }
    }

}




