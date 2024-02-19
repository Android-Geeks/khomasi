package com.company.khomasi.presentation.components.booking_cards

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.presentation.components.BookingDetails
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.MyOutlinedButton
import com.company.khomasi.presentation.components.Playground
import com.company.khomasi.theme.KhomasiTheme

@Composable
fun ExpiredBookingCard (
    bookingDetails: BookingDetails,
    modifier: Modifier = Modifier
){
    Card(
        modifier
            .height(432.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
        colors = CardDefaults.cardColors(Color.Unspecified),
        elevation = CardDefaults.cardElevation(1.dp)

    ){
        Box(
            modifier =  Modifier.fillMaxSize()
        ) {

            Image(
                painter = painterResource(R.drawable.tacket_rect),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize(),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surface)
            )
            Column {
                BookingCard(bookingDetails, Modifier)

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, bottom = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    MyButton(
                        text = R.string.rebook,
                        onClick = { },
                        icon = R.drawable.arrowscounterclockwise,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    MyOutlinedButton(
                        text = R.string.rate_field,
                        onClick = { },
                        icon = R.drawable.chatcircledots,
                        modifier = Modifier.weight(1f)
                    )

                }
            }
        }
    }

}
@Preview
@Composable
fun ExpiredBookingCardPreview(){
    KhomasiTheme (darkTheme = false){
        ExpiredBookingCard(
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