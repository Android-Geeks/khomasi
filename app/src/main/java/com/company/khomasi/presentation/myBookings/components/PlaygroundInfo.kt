package com.company.khomasi.presentation.myBookings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.BookingDetails
import com.company.khomasi.domain.model.MyBookingsResponse
import com.company.khomasi.domain.model.PlaygroundPicture
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.cards.BookingCard
import com.company.khomasi.presentation.components.cards.BookingStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaygroundInfo(
    bookingDetails: BookingDetails,
    myBooking: DataState<MyBookingsResponse>,
    playgroundPicture: PlaygroundPicture,

    ) {
    Column {
        TopAppBar(
            title = {
                Text(
                    text = bookingDetails.name,
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.CenterStart)
                        .padding(start = 16.dp)
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.background)
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(), thickness = 1.dp
        )
        if (myBooking is DataState.Success) {
            BookingCard(
                bookingDetails = myBooking.data.results[bookingDetails.playgroundId],
                playgroundPicture = playgroundPicture,
                bookingStatus = BookingStatus.CONFIRMED
            )
        }
        Spacer(modifier = Modifier.height(141.dp))
        MyButton(text = R.string.booking_cancelled, onClick = { /*TODO*/ })
        Spacer(modifier = Modifier.height(56.dp))


    }

}
