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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.MyBookingsResponse
import com.company.khomasi.presentation.components.cards.BookingCard
import com.company.khomasi.presentation.components.cards.BookingStatus
import com.company.khomasi.presentation.myBookings.MockViewModel
import com.company.khomasi.presentation.myBookings.MyBookingUiState
import com.company.khomasi.theme.KhomasiTheme
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ExpiredPage(
    uiState: StateFlow<MyBookingUiState>,
    myBooking: DataState<MyBookingsResponse>,
    myBookingPlaygrounds: () -> Unit,
    onClickPlaygroundCard: (Int) -> Unit
) {
//    LaunchedEffect(key1 = Unit) {
//        myBookingPlaygrounds()
//    }
    val currentState by uiState.collectAsState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp),
            color = MaterialTheme.colorScheme.background,
        ) {
            //    if (currentState.bookingPlayground.isNotEmpty()) {
                LazyColumn(
                    contentPadding = it,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(currentState.bookingPlayground) {
                        if (currentState.isFinished) {
                            BookingCard(
                                bookingDetails = it,
                                bookingStatus = BookingStatus.EXPIRED,
                                onViewPlaygroundClick = {}
                            )
                        }
                    }
                }
//            } else {
//                EmptyScreen()
//            }
        }
    }
}

@Preview
@Composable
private fun ExpiredPagePreview() {
    val myMockViewModel: MockViewModel = hiltViewModel()
    KhomasiTheme {
        ExpiredPage(
            uiState = myMockViewModel.uiState,
            myBooking = myMockViewModel.myBooking.collectAsState().value,
            myBookingPlaygrounds = myMockViewModel::myBookingPlaygrounds
        ) {

        }
    }

}