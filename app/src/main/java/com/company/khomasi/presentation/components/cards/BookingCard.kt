package com.company.khomasi.presentation.components.cards

import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import com.company.khomasi.domain.model.PlaygroundPicture
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.MyOutlinedButton
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.theme.darkSubText
import com.company.khomasi.theme.darkWarningColor
import com.company.khomasi.theme.lightSubText
import com.company.khomasi.theme.lightWarningColor
import com.company.khomasi.utils.extractDateFromTimestamp
import com.company.khomasi.utils.extractTimeFromTimestamp
import com.company.khomasi.utils.parseTimestamp


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookingCard(
    playgroundName: String,
    playgroundAddress: String,
    playgroundPicture: PlaygroundPicture,
    playgroundPrice: Int,
    playgroundBookingTime: String,
    isCanceled: Boolean,
    confirmationCode: String,
    modifier: Modifier = Modifier,
    isDark: Boolean = isSystemInDarkTheme(),
    bookingStatus: BookingStatus,
    showPendingButton: Boolean = true,
) {
    val parsedBookingTime = parseTimestamp(playgroundBookingTime)
    val bookingTime = extractTimeFromTimestamp(parsedBookingTime)
    val bookingDate = extractDateFromTimestamp(parsedBookingTime)
    Card(
        modifier
            .height(
                if (bookingStatus == BookingStatus.CONFIRMED) 482.dp else 432.dp
            )
            .fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
        colors = CardDefaults.cardColors(Color.Transparent)
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
                BookingCardDetails(
                    playgroundName = playgroundName,
                    playgroundAddress = playgroundAddress,
                    playgroundBookingDate = bookingDate,
                    playgroundBookingTime = bookingTime,
                    playgroundPrice = playgroundPrice,
                    playgroundPicture = playgroundPicture,
                )

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
                                        text = confirmationCode,
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

                        BookingStatus.PENDING -> {
                            if (showPendingButton) {
                                MyButton(
                                    text = R.string.waiting,
                                    onClick = { },
                                    shape = MaterialTheme.shapes.medium,
                                    modifier = Modifier
                                        .padding(start = 92.dp, end = 92.dp)
                                        .weight(1f),
                                    color = if (isDark) ButtonDefaults.buttonColors(darkWarningColor) else ButtonDefaults.buttonColors(
                                        lightWarningColor
                                    ),
                                )
                            }
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
                                onClick = { },
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
    playgroundName: String,
    playgroundAddress: String,
    playgroundBookingDate: String,
    playgroundBookingTime: String,
    playgroundPrice: Int,
    playgroundPicture: PlaygroundPicture,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
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
                .data(playgroundPicture.picture)
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
                text = playgroundName,
                iconId = R.drawable.soccerball
            )
            TextWithIcon(
                text = playgroundAddress,
                iconId = R.drawable.mappin
            )
            TextWithIcon(
                text = playgroundBookingDate,
                iconId = R.drawable.calendar
            )
            TextWithIcon(
                text = playgroundBookingTime,
                iconId = R.drawable.clock
            )
            TextWithIcon(
                text = context.getString(
                    R.string.fees_per_hour, playgroundPrice
                ),
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview(name = "Night", uiMode = UI_MODE_NIGHT_YES)
@Preview(name = "Light", uiMode = UI_MODE_NIGHT_NO, locale = "ar")
@Composable
private fun BookingCardPreview() {
    KhomasiTheme {
        BookingCard(
            playgroundName = "Al Zamalek Club",
            playgroundAddress = "Nasr City",
            playgroundBookingTime = "2024-04-23T12:00:00",
            playgroundPrice = 50,
            confirmationCode = "2425",
            isCanceled = false,
            playgroundPicture = PlaygroundPicture(
                id = 1,
                playgroundId = 1,
                picture = " ",
                isDocumentation = false
            ),
            bookingStatus = BookingStatus.PENDING
        )
    }
}
