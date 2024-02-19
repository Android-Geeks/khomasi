package com.company.khomasi.presentation.components.booking_cards

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.theme.KhomasiTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.company.khomasi.presentation.components.BookingDetails
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.Playground
import com.company.khomasi.theme.lightSubText

@Composable
fun RecentlyConfirmedBookingCard (
    bookingDetails: BookingDetails,
    modifier: Modifier = Modifier
) {
    Card(
        modifier
            .height(482.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
        colors = CardDefaults.cardColors(Color.Unspecified),
        elevation = CardDefaults.cardElevation(1.dp)
        ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(R.drawable.tacket_rect),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxSize()
            )
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {

                BookingCard(
                    bookingDetails,
                    Modifier.fillMaxWidth()
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                ) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                            .height(1.dp)
                            .border(width = 1.dp, color = Color(0xff838485))
                    )

                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.key),
                            contentDescription = null,
                            tint = lightSubText,
                            modifier = Modifier.size(16.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(id = R.string.verification_code),
                            style = MaterialTheme.typography.titleSmall,
                            color = lightSubText,
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.weight(1F))

                        Text(
                            text = bookingDetails.verificationCode,
                            style = MaterialTheme.typography.displayLarge,
                            color = MaterialTheme.colorScheme.secondary,
                            textAlign = TextAlign.End,

                            )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        MyButton(
                            text = R.string.booking_confirmed,
                            onClick = {  },
                            Modifier.padding(horizontal = 85.dp)
                        )
                    }
                }
            }

        }
    }
}
@Preview
@Composable
fun RecentlyConfirmedBookingCardPreview () {
    KhomasiTheme{
        RecentlyConfirmedBookingCard(
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
            ),
        )

    }

}
