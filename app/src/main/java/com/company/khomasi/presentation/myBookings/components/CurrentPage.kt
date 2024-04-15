package com.company.khomasi.presentation.myBookings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.company.khomasi.presentation.components.cards.BookingCard
import com.company.khomasi.presentation.components.cards.BookingStatus
import com.company.khomasi.presentation.myBookings.MyBookingUiState
import kotlinx.coroutines.flow.StateFlow

val LocalMyBookingUiState =
    staticCompositionLocalOf<StateFlow<MyBookingUiState>> {
        error("No MyBookingUiState provided")
    }
@Composable
fun CurrentPage(
    //uiState: StateFlow<MyBookingUiState>,
    //myBooking: StateFlow<DataState<MyBookingsResponse>>,

    ) {
    val uiState = LocalMyBookingUiState.current
    val currentState by uiState.collectAsState()
    // val myBookingState by myBooking.collectAsState()


    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp),
            color = MaterialTheme.colorScheme.background,
        ) {
            if (currentState.bookingPlayground.isNotEmpty()) {
                LazyColumn(
                    contentPadding = it,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(currentState.bookingPlayground) {
                        BookingCard(
                            bookingDetails = it,
                            bookingStatus = BookingStatus.CONFIRMED
                        )

                    }
                }
            }
        }
    }
}