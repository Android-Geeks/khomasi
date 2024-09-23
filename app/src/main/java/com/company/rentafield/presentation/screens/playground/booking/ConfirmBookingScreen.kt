package com.company.rentafield.presentation.screens.playground.booking

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.rentafield.R
import com.company.rentafield.presentation.components.AuthSheet
import com.company.rentafield.presentation.components.MyButton
import com.company.rentafield.presentation.components.cards.BookingStatus
import com.company.rentafield.presentation.screens.playground.components.PlaygroundBookingCard
import com.company.rentafield.presentation.theme.RentafieldTheme
import com.company.rentafield.utils.extractTimeFromTimestamp
import com.company.rentafield.utils.parseTimestamp
import com.company.rentafield.utils.screenDimensions.getScreenHeight
import com.company.rentafield.utils.screenDimensions.getScreenWidth
import kotlinx.coroutines.flow.StateFlow


@Composable
fun ConfirmBookingScreen(
    bookingUiState: StateFlow<BookingUiState>,
    context: Context = LocalContext.current,
    onBackClicked: () -> Unit,
    onNextClicked: () -> Unit,
) {
    val bookingState by bookingUiState.collectAsStateWithLifecycle()
    val screenHeight = getScreenHeight(context)

    Scaffold(
        topBar = {
            BookingTopBar(
                playgroundName = bookingState.playgroundName,
                onBackClicked = onBackClicked,
                context = context
            )
        },
        modifier = Modifier.fillMaxSize(),

        ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            AuthSheet(
                modifier = Modifier
                    .fillMaxSize(),
                sheetModifier = Modifier.fillMaxWidth(),
                screenContent = {
                    ConfirmBookingContent(
                        bookingState = bookingState,
                        context = context,
                        modifier = Modifier.padding(paddingValues),
                    )
                },
                sheetContent = {
                    ConfirmBookingBottomSheet(
                        sheetHeight = (screenHeight * 0.16).dp,
                        playgroundPrice = bookingState.totalPrice,
                        onContinueToPaymentClicked = { onNextClicked() }
                    )
                }
            )


        }
    }
}

@Composable
fun ConfirmBookingContent(
    bookingState: BookingUiState,
    onClickTermsOfService: () -> Unit = {},
    context: Context,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    val screenWidth = getScreenWidth(context)
    val screenHeight = getScreenHeight(context)
    Column(
        modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val bookingDuration = if (bookingState.selectedSlots.size > 0) {
            extractTimeFromTimestamp(parseTimestamp(bookingState.selectedSlots.minOf { it.first }
                .toString())) +
                    " ${stringResource(id = R.string.to)} " +
                    extractTimeFromTimestamp(parseTimestamp(bookingState.selectedSlots.maxOf { it.second }
                        .toString()))

        } else {
            ""
        }
        PlaygroundBookingCard(
            playgroundName = bookingState.playgroundName,
            playgroundAddress = bookingState.playgroundAddress,
            playgroundBookingTime = bookingState.bookingTime,
            bookingDuration = bookingDuration,
            playgroundPrice = bookingState.totalPrice,
            playgroundPicture = bookingState.playgroundMainPicture,
            bookingStatus = BookingStatus.CONFIRMED,
            modifier = Modifier
                .width((screenWidth * 0.92).dp)
                .height((screenHeight * 0.58).dp),
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = stringResource(id = R.string.agree_to_terms_part1),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.tertiary,
            )
            Text(text = stringResource(id = R.string.terms_of_service),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable { onClickTermsOfService() })
        }
    }
}


@Composable
fun ConfirmBookingBottomSheet(
    sheetHeight: Dp,
    playgroundPrice: Int,
    onContinueToPaymentClicked: () -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .height(sheetHeight),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.total_amount_label),
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "$playgroundPrice " + stringResource(id = R.string.price_egp),
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        MyButton(
            text = R.string.continue_to_payment,
            onClick = { onContinueToPaymentClicked() },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.displayLarge
        )

    }
}

@Preview(
    name = "DARK | EN",
    locale = "en",
    uiMode = UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF0E0E0E,
    showBackground = true
)
@Preview(
    name = "LIGHT | AR",
    locale = "ar",
    uiMode = UI_MODE_NIGHT_NO,
    backgroundColor = 0xFFF5F5F5,
    showBackground = true
)
@Composable
fun ConfirmBookingScreenPreview() {
    RentafieldTheme {
        val mockBookingViewModel = MockBookingViewModel()
        ConfirmBookingContent(
            bookingState = mockBookingViewModel.bookingUiState.collectAsState().value,
            onClickTermsOfService = {},
            context = LocalContext.current
        )
    }
}