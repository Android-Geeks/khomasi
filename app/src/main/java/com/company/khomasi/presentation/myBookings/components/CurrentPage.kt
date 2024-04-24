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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.company.khomasi.presentation.components.cards.BookingCard
import com.company.khomasi.presentation.components.cards.BookingStatus
import com.company.khomasi.presentation.myBookings.MyBookingUiState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CurrentPage(
    uiState: StateFlow<MyBookingUiState>,
    onClickPlaygroundCard: (Int) -> Unit,
    onBackClick: () -> Unit,
) {
    val selectedPlaygroundIdState = remember { mutableStateOf<Int?>(null) }

    val currentState = uiState.collectAsState().value
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp),
            color = MaterialTheme.colorScheme.background,
        ) {
            if (currentState.currentBookings.isNotEmpty()) {
                LazyColumn(
                    contentPadding = it,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    itemsIndexed(currentState.currentBookings) { index, it ->
                        BookingCard(
                            bookingDetails = it,
                            bookingStatus = if (!currentState.currentBookings[index].isCanceled) BookingStatus.CONFIRMED else BookingStatus.CANCEL,
                            onViewPlaygroundClick = {
                                onClickPlaygroundCard(it.playgroundId)
                                selectedPlaygroundIdState.value = it.playgroundId

                            }
                        )
//                        BookingCard(
//                            bookingDetails = it,
//                            bookingStatus = BookingStatus.CANCEL,
//                            onViewPlaygroundClick = {
//                                onClickPlaygroundCard(it.playgroundId)
//                            },
//                            toRate = {}
//                        )
                    }
                }
            } else {
                EmptyScreen()
            }
        }
        selectedPlaygroundIdState.value?.let { playgroundId ->
            ConfirmationBottomSheet(
                bookingDetails = currentState.currentBookings.find { it.playgroundId == playgroundId },
                onBackClick = onBackClick,
            )
        }
    }
}



