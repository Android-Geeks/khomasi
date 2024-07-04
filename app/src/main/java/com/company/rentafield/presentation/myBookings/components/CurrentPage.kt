package com.company.rentafield.presentation.myBookings.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.rentafield.R
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.booking.BookingDetails
import com.company.rentafield.domain.model.booking.MyBookingsResponse
import com.company.rentafield.presentation.components.cards.BookingCard
import com.company.rentafield.presentation.components.cards.BookingStatus
import com.company.rentafield.presentation.components.connectionStates.ThreeBounce
import com.company.rentafield.presentation.myBookings.MyBookingUiState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CurrentPage(
    bookingsUiState: StateFlow<MyBookingUiState>,
    myBookingsState: StateFlow<DataState<MyBookingsResponse>>,
    onClickPlaygroundCard: (BookingDetails) -> Unit,
    onClickBookField: () -> Unit,
    cancelDetails: (Int, Boolean) -> Unit
) {
    val uiState by bookingsUiState.collectAsStateWithLifecycle()
    val currentState by myBookingsState.collectAsStateWithLifecycle()
    var showLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current
    LaunchedEffect(currentState) {
        Log.d("CurrentPage", "Current state: $currentState")
        when (currentState) {
            is DataState.Success -> {
                showLoading = false
            }

            DataState.Loading -> {
                showLoading = true
            }

            is DataState.Error -> {
                showLoading = false
                Toast.makeText(context, R.string.booking_failure, Toast.LENGTH_SHORT).show()
            }

            DataState.Empty -> {}
        }
    }
    Box {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {}
            itemsIndexed(uiState.currentBookings) { index, it ->
                if (uiState.currentBookings.isNotEmpty()) {
                    BookingCard(
                        bookingDetails = it,
                        bookingStatus = if (!uiState.currentBookings[index].isCanceled) BookingStatus.CONFIRMED else BookingStatus.CANCEL,
                        onViewPlaygroundClick = {
                            if (!uiState.currentBookings[index].isCanceled) {
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
        if (showLoading) {
            ThreeBounce(modifier = Modifier.fillMaxSize())
        }
    }
}




