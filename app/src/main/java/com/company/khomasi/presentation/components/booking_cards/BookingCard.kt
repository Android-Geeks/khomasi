package com.company.khomasi.presentation.components.booking_cards

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.company.khomasi.R
import com.company.khomasi.presentation.components.BookingDetails
import com.company.khomasi.presentation.components.Playground
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.theme.lightSubText

@Composable
fun BookingCard(
    bookingDetails: BookingDetails,
    modifier : Modifier
){
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
        colors = CardDefaults.cardColors(Color.Unspecified)

    ){
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(bookingDetails.playground.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
//                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.playground_image)
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
                    TextWithIcon(
                        text = bookingDetails.playground.name,
                        iconId = R.drawable.soccerball
                    )

                    TextWithIcon(
                        text = bookingDetails.playground.address,
                        iconId = R.drawable.mappin
                    )

                    TextWithIcon(text = bookingDetails.date, iconId = R.drawable.calendar)

                    TextWithIcon(text = bookingDetails.time, iconId = R.drawable.clock)

                    TextWithIcon(
                        text = bookingDetails.playground.price,
                        iconId = R.drawable.currencycircledollar
                    )

                }
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

@Preview
@Composable
fun BookingCardPreview(){
    KhomasiTheme() {
        BookingCard(
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
            Modifier
        )
    }
}
