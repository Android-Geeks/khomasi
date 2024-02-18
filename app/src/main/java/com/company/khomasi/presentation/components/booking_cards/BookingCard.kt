package com.company.khomasi.presentation.components.booking_cards

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.theme.lightSubText

@Composable
fun BookingCard(
    @DrawableRes playgroundNameImgId : Int,
     playgroundName: String,
     location : String,
     date : String,
     time : String,
     price : String
){
        Column(
            modifier = Modifier
                .width(358.dp)

            , horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(id = playgroundNameImgId),
                contentDescription = null,
                modifier = Modifier
                    .width(358.dp)
                    .height(120.dp)
            )

             Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 8.dp, end = 8.dp, top = 16.dp
                    )
            ) {
                Column()
                {
                    TextWithIcon(text = playgroundName, iconId = R.drawable.soccerball)

                    TextWithIcon(text = location, iconId = R.drawable.mappin)

                    TextWithIcon(text = date, iconId = R.drawable.calendar)

                    TextWithIcon(text = time, iconId = R.drawable.clock)

                    TextWithIcon(text = price, iconId = R.drawable.currencycircledollar)




                }
            }
        }



}
@Composable
fun TextWithIcon(
    text : String,
    @DrawableRes iconId : Int,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = lightSubText,
            modifier = Modifier.size(16.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            color = lightSubText,
            textAlign = TextAlign.Start
        )
    }
}

@Preview()
@Composable
fun BookingCardPreview(){
    KhomasiTheme(darkTheme = true) {

        BookingCard(
            R.drawable.playground_image,
            "Zsc",
            "Tanta",
            "1/10/2024",
            "7 AM to 8 AM",
            "50 $ per hour "
        )
    }
}