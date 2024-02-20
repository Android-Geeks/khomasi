package com.company.khomasi.presentation.components.booking_cards

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.company.khomasi.R
import com.company.khomasi.presentation.components.BookingDetails
import com.company.khomasi.presentation.components.BookingStatus
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.MyOutlinedButton
import com.company.khomasi.presentation.components.Playground
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.theme.darkSubText
import com.company.khomasi.theme.lightSubText


@Composable
fun BookingCard(
    bookingDetails: BookingDetails,
    modifier: Modifier = Modifier,
    isDark: Boolean = isSystemInDarkTheme()
) {
    Card(
        modifier
            .height(
                if (bookingDetails.statusOfBooking == BookingStatus.CONFIRMED) 482.dp else 432.dp
            )
            .fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
        colors = CardDefaults.cardColors(Color.Unspecified),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Image(
                painter = painterResource(R.drawable.ticket_rect),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize(),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surface)
            )
            Column {
                BookingCardDetails(bookingDetails)

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, bottom = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    when (bookingDetails.statusOfBooking) {
                        BookingStatus.CONFIRMED -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
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
                                        tint = if (isDark) darkSubText else lightSubText,
                                        modifier = Modifier.size(16.dp)
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = stringResource(id = R.string.verification_code),
                                        style = MaterialTheme.typography.titleSmall,
                                        color = if (isDark) darkSubText else lightSubText,
                                        textAlign = TextAlign.Start
                                    )
                                    Spacer(modifier = Modifier.weight(1f))

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
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    MyButton(
                                        text = R.string.booking_confirmed,
                                        onClick = { },
                                        Modifier.padding(horizontal = 85.dp)
                                    )
                                }
                            }
                        }

                        BookingStatus.PENDING -> {
                            MyButton(
                                text = R.string.waiting,
                                onClick = { },
                                modifier = Modifier
                                    .padding(start = 92.dp, end = 92.dp)
                                    .weight(1f)
                            )
                        }

                        BookingStatus.EXPIRED -> {
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
    }

}

@Composable
fun BookingCardDetails(
    bookingDetails: BookingDetails,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 8.dp, end = 8.dp, top = 16.dp
                )
        )
        {
            TextWithIcon(
                text = bookingDetails.playground.name,
                iconId = R.drawable.soccerball
            )
            TextWithIcon(
                text = bookingDetails.playground.address,
                iconId = R.drawable.mappin
            )
            TextWithIcon(
                text = bookingDetails.date,
                iconId = R.drawable.calendar
            )
            TextWithIcon(
                text = bookingDetails.time,
                iconId = R.drawable.clock
            )
            TextWithIcon(
                text = bookingDetails.playground.price,
                iconId = R.drawable.currencycircledollar
            )
        }
    }
}

@Composable
fun TextWithIcon(
    text: String,
    @DrawableRes iconId: Int,
    isDark : Boolean = isSystemInDarkTheme()
) {
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
            tint = if (isDark) darkSubText else lightSubText,
            modifier = Modifier.size(16.dp)
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            color = if (isDark) darkSubText else lightSubText,
            textAlign = TextAlign.Start
        )
    }
}

@Preview(name = "Night", uiMode = UI_MODE_NIGHT_YES)
@Preview(name = "Light", uiMode = UI_MODE_NIGHT_NO, locale = "ar")
@Composable
private fun BookingCardPreview() {
    KhomasiTheme {
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
                    isFavorite = true,
                    isBookable = false
                ),
                statusOfBooking = BookingStatus.CONFIRMED
            ),
        )
    }
}
