package com.company.khomasi.presentation.components.booking_cards

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.theme.KhomasiTheme

@Composable
fun BookingCard(
     playgroundName: String
){

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(358.dp)
//            .size(width = 358.dp, height = 432.dp)
        ,horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top

    ) {
        Image(
            painter = painterResource(id = R.drawable.playground_image),
            contentDescription = null,
            modifier = Modifier
                .width(358.dp)
                .height(120.dp)
        )

        Box(
            modifier = Modifier.size(width = 358.dp, height = 312.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.hollow_rec),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 8.dp, end = 8.dp, top = 16.dp
                    )){
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.soccerball),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(text = playgroundName)

                }
            }
        }
    }


}

@Preview(showSystemUi = true)
@Composable
fun ExpiredBookingCardPreview(){
    KhomasiTheme() {

        BookingCard("Zsc")
    }
}