package com.company.khomasi.presentation.components.booking_cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.presentation.components.BookingDetails
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.Playground
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.theme.darkCard
import com.company.khomasi.theme.lightCard

@Composable
fun PendingBookingCard(
    bookingDetails : BookingDetails
){
    Box(
        Modifier
            .height(432.dp)
            .width(358.dp)
    ) {

        Image(
            painter = painterResource(R.drawable.tacket_rect),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize(),
            colorFilter = if (isSystemInDarkTheme()) ColorFilter.tint(darkCard) else ColorFilter.tint(
                lightCard)

        )
        Column(Modifier.fillMaxWidth()) {
            BookingCard(
            bookingDetails
            )
            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                MyButton(
                    text = R.string.waiting,
                    onClick = { },
                    modifier = Modifier
                        .padding(start = 92.dp, end = 92.dp)
                        .weight(1f)
                )
            }
        }
    }
}

@Preview
@Composable
fun PendingBookingCardPre() {
    KhomasiTheme(darkTheme = true) {
        PendingBookingCard(
            BookingDetails(
                "1/10/2024",
                "7 AM to 8 AM",
                "50 $ per hour ",
                "2425",
                Playground(
                    "Zsc",
                    "Tanta",
                    "https://2u.pw/KqnLykO",
                    3.8f,
                    "50 $ per hour",
                    "from 12 PM to 12 AM",
                    true,
                    false
                ),
                "_"
            )
        )
    }
}
