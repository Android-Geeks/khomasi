package com.company.khomasi.presentation.components.booking_cards

import androidx.annotation.DrawableRes
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.theme.lightSubText

@Composable
fun RecentlyConfirmedBookingCard (
    @DrawableRes playgroundImageId : Int,
    playgroundName: String,
    location : String,
    date : String,
    time : String,
    price : String,
    verificationCode : String
) {
    Box(
        Modifier
            .height(482.dp)
            .width(358.dp)) {

        Image(
            painter = painterResource(R.drawable.tacket_rect),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()

        )
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {

            BookingCard(
                playgroundImageId,
                playgroundName = playgroundName,
                location = location,
                date = date,
                time = time,
                price = price
            )

            Column(
                modifier = Modifier
                    .width(358.dp)
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
                ){
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
                        text = verificationCode,
                        style = MaterialTheme.typography.displayLarge,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.End,

                        )
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding( bottom = 20.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    MyButton(
                        text = stringResource(id = R.string.booking_confirmed),
                        onClick = { /*TODO*/ },
                        Modifier.padding(horizontal = 85.dp))
                }
            }
        }

    }
}

@Preview
@Composable
fun RecentlyConfirmedBookingCardPreview () {
    KhomasiTheme(darkTheme = false){
        RecentlyConfirmedBookingCard(
            R.drawable.playground_image,
            "Zsc",
            "Tanta",
            "1/10/2024",
            "7 AM to 8 AM",
            "50 $ per hour ",
            "2425"
        )
    }

}