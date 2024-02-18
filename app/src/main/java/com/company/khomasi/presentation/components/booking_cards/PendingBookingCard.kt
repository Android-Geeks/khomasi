package com.company.khomasi.presentation.components.booking_cards

import androidx.annotation.DrawableRes
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.MyOutlinedButton
import com.company.khomasi.theme.KhomasiTheme

@Composable
fun PendingBookingCard(
    @DrawableRes playgroundImageId : Int,
    playgroundName: String,
    location : String,
    date : String,
    time : String,
    price : String,
    ){
        Box(
            Modifier
                .height(432.dp)
                .width(358.dp)) {

            Image(
                painter = painterResource(R.drawable.tacket_rect),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()

            )
            Column() {
                BookingCard(
                    playgroundImageId,
                    playgroundName = playgroundName,
                    location = location,
                    date = date,
                    time = time,
                    price = price
                )
                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    MyButton(
                        text = stringResource(id = R.string.waiting),
                        onClick = { /*TODO*/ },
                        modifier = Modifier.padding(start = 92.dp, end = 92.dp)
                            .weight(1f))
                }
            }
        }

}

@Preview
@Composable
fun PendingBookingCardPre() {
    KhomasiTheme {
        PendingBookingCard(
            R.drawable.playground_image,
            "Zsc",
            "Tanta",
            "1/10/2024",
            "7 AM to 8 AM",
            "50 $ per hour ")
    }
}
