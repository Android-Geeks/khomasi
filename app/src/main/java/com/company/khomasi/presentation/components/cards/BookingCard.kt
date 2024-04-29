package com.company.khomasi.presentation.components.cards

import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.company.khomasi.R
import com.company.khomasi.domain.model.BookingDetails
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.MyOutlinedButton
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.theme.darkErrorColor
import com.company.khomasi.theme.darkSubText
import com.company.khomasi.theme.lightErrorColor
import com.company.khomasi.theme.lightSubText
import com.company.khomasi.utils.convertToBitmap

@Composable
fun BookingCard(
    bookingDetails: BookingDetails,
    modifier: Modifier = Modifier,
    onViewPlaygroundClick: () -> Unit,
    isDark: Boolean = isSystemInDarkTheme(),
    bookingStatus: BookingStatus,
    toRate: () -> Unit,
    onClickPlaygroundCard: (Int) -> Unit,

    ) {
    Card(
        modifier
            .height(
                if (bookingStatus == BookingStatus.CONFIRMED) 482.dp else 432.dp
            )
            .fillMaxWidth()
            .clickable {
                onViewPlaygroundClick()
            },
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
                    when (bookingStatus) {
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
                                        text = bookingDetails.confirmationCode,
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
                                        shape = MaterialTheme.shapes.medium,
                                        color = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
                                    )
                                }
                            }
                        }

                        BookingStatus.CANCEL -> {
                            MyButton(
                                text = R.string.booking_cancelled,
                                onClick = { },
                                shape = MaterialTheme.shapes.medium,
                                modifier = Modifier
                                    .padding(start = 92.dp, end = 92.dp)
                                    .weight(1f),
                                color = if (isDark) ButtonDefaults.buttonColors(darkErrorColor) else ButtonDefaults.buttonColors(
                                    lightErrorColor
                                ),
                            )
                        }

                        BookingStatus.EXPIRED -> {
                            MyButton(
                                text = R.string.rebook,
                                onClick = { },
                                shape = MaterialTheme.shapes.medium,
                                icon = R.drawable.arrowscounterclockwise,
                                modifier = Modifier
                                    .weight(1f)
                                    .height(46.dp),
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            MyOutlinedButton(
                                text = R.string.rate_field,
                                onClick = {
                                    toRate()
                                    onClickPlaygroundCard(bookingDetails.playgroundId)
                                },
                                icon = R.drawable.chatcircledots,
                                modifier = Modifier
                                    .weight(1f),
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
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
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
                .data(bookingDetails.playgroundPicture.convertToBitmap())
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
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
                text = bookingDetails.playgroundName,
                iconId = R.drawable.soccerball
            )
            TextWithIcon(
                text = bookingDetails.playgroundAddress,
                iconId = R.drawable.mappin
            )
            TextWithIcon(
                text = bookingDetails.bookingTime,
                iconId = R.drawable.calendar
            )
            TextWithIcon(
                text = bookingDetails.bookingTime,
                iconId = R.drawable.clock
            )
            TextWithIcon(
                text = context.getString(R.string.fees_per_hour, bookingDetails.cost),
                iconId = R.drawable.currencycircledollar
            )
        }
    }
}

@Composable
fun TextWithIcon(
    text: String,
    @DrawableRes iconId: Int,
    isDark: Boolean = isSystemInDarkTheme()
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
            bookingDetails = BookingDetails(
                1,
                1,
                "Al Zamalek Club",
                "Nasr City",
                "1/10/2024",
                "7",
                50,
                2425,
                "false",
                false,
                false
            ),

            bookingStatus = BookingStatus.CONFIRMED,
            onViewPlaygroundClick = {},
            toRate = {},
            onClickPlaygroundCard = {}
        )

    }
}
