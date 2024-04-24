package com.company.khomasi.presentation.booking

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.domain.model.BookingDetails
import com.company.khomasi.domain.model.PlaygroundPicture
import com.company.khomasi.presentation.components.AuthSheet
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.cards.BookingCard
import com.company.khomasi.presentation.components.cards.BookingStatus
import com.company.khomasi.presentation.screenDimensions.getScreenHeight
import com.company.khomasi.presentation.screenDimensions.getScreenWidth
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.theme.darkText
import com.company.khomasi.theme.lightText
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ConfirmBookingScreen(
    bookingUiState: StateFlow<BookingUiState>,
    context: Context = LocalContext.current,
    onClickTermsOfService: () -> Unit = {},
    isDark: Boolean = isSystemInDarkTheme(),
    onBackClicked: () -> Unit = {}
) {
    val bookingState = bookingUiState.collectAsState().value
    Scaffold(
        topBar = {
            BookingTopBar(
                playgroundName = bookingState.playgroundName,
                context = context,
                onBackClicked = onBackClicked
            )
        }) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background,
        ) {
            AuthSheet(screenContent = { ConfirmBookingContent(onClickTermsOfService = { onClickTermsOfService() }) },
                sheetContent = {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .height(116.dp),
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
                                color = if (isDark) darkText else lightText
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = context.getString(
                                    R.string.fees_per_hour, bookingState.playgroundPrice
                                ),
                                style = MaterialTheme.typography.displayLarge,
                                color = if (isDark) darkText else lightText
                            )
                        }

                        MyButton(
                            text = R.string.continue_to_payment,
                            onClick = { },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = MaterialTheme.typography.displayLarge
                        )

                    }
                })

        }
    }
}

@Composable
fun ConfirmBookingContent(
    onClickTermsOfService: () -> Unit = {}
) {
    val screenWidth = getScreenWidth()
    val screenHeight = getScreenHeight()
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BookingCard(
            modifier = Modifier
                .width((screenWidth * 0.92).dp)
                .height((screenHeight * 0.58).dp),
            bookingDetails = BookingDetails(
                1, 1, "Al Zamalek Club", "Nasr City", "1/10/2024", 7, 50, "2425", false
            ),
            playgroundPicture = PlaygroundPicture(
                1, 1, " ", false
            ),
            bookingStatus = BookingStatus.PENDING,
            showPendingButton = false,
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


@Preview(showSystemUi = true, locale = "en")
@Composable
fun ConfirmBookingScreenPreview() {
    KhomasiTheme {
        val mockBookingViewModel = MockBookingViewModel()
        ConfirmBookingScreen(mockBookingViewModel.bookingUiState)
    }
}