package com.company.khomasi.presentation.playground.components


import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.company.khomasi.R
import com.company.khomasi.presentation.components.cards.BookingStatus
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.utils.convertToBitmap
import com.company.khomasi.utils.extractDateFromTimestamp
import com.company.khomasi.utils.parseTimestamp


@Composable
fun PlaygroundBookingCard(
    playgroundName: String,
    playgroundAddress: String,
    playgroundPicture: String,
    playgroundPrice: Int,
    playgroundBookingTime: String,
    bookingDuration: String,
    modifier: Modifier = Modifier,
    bookingStatus: BookingStatus,
) {
    val parsedBookingTime = parseTimestamp(playgroundBookingTime)
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
                PlaygroundBookingCardDetails(
                    playgroundName = playgroundName,
                    playgroundAddress = playgroundAddress,
                    playgroundBookingDate = bookingDate,
                    playgroundBookingTime = bookingDuration,
                    playgroundPrice = playgroundPrice,
                    playgroundPicture = playgroundPicture,
                )

                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }

}

@Composable
fun PlaygroundBookingCardDetails(
    playgroundName: String,
    playgroundAddress: String,
    playgroundBookingDate: String,
    playgroundBookingTime: String,
    playgroundPrice: Int,
    playgroundPicture: String,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(playgroundPicture.convertToBitmap()).crossfade(true).build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            loading = { CircularProgressIndicator() },
            error = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
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
            tint = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.size(16.dp)
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.tertiary,
            textAlign = TextAlign.Start
        )
    }
}

@Preview(name = "Night", uiMode = UI_MODE_NIGHT_YES)
@Preview(name = "Light", uiMode = UI_MODE_NIGHT_NO, locale = "ar")
@Composable
private fun BookingCardPreview() {
    KhomasiTheme {
        PlaygroundBookingCard(
            playgroundName = "Al Zamalek Club",
            playgroundAddress = "Nasr City",
            playgroundBookingTime = "2024-04-23T12:00:00",
            playgroundPrice = 50,
            playgroundPicture = "",
            bookingStatus = BookingStatus.CONFIRMED,
            bookingDuration = ""
        )
    }
}
