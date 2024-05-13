package com.company.khomasi.presentation.myBookings.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.khomasi.R
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.MyModalBottomSheet
import com.company.khomasi.presentation.components.MyTextButton
import com.company.khomasi.presentation.components.SubScreenTopBar
import com.company.khomasi.presentation.components.cards.BookingCard
import com.company.khomasi.presentation.components.cards.BookingStatus
import com.company.khomasi.presentation.myBookings.MyBookingUiState
import com.company.khomasi.theme.Cairo
import com.company.khomasi.theme.darkErrorColor
import com.company.khomasi.theme.darkText
import com.company.khomasi.theme.lightErrorColor
import com.company.khomasi.theme.lightText
import kotlinx.coroutines.flow.StateFlow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CancelBookingPage(
    uiState: StateFlow<MyBookingUiState>,
    onBackClick: () -> Unit,
    cancelBooking: (Int) -> Unit,
    isDark: Boolean = isSystemInDarkTheme()
) {
    val details = uiState.collectAsStateWithLifecycle().value.cancelBookingDetails
    val sheetState = rememberModalBottomSheetState()
    var isOpen by remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (isOpen) {
        MyModalBottomSheet(sheetState = sheetState, onDismissRequest = { isOpen = false }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.padding(top = 12.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.info),
                        contentDescription = null,
                        modifier = Modifier.padding(end = 4.dp, top = 8.dp)
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontFamily = Cairo,
                                    fontWeight = FontWeight(500),
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            ) {
                                append(stringResource(id = R.string.confirm_cancel_booking) + "\n")
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontFamily = Cairo,
                                    fontWeight = FontWeight(500),
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.tertiary
                                )
                            ) {
                                append(stringResource(id = R.string.action_will_cancel_booking))
                            }
                        },
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MyTextButton(
                        text = R.string.back,
                        isUnderlined = false,
                        textColor = if (isDark) darkText else lightText,
                        onClick = { isOpen = false },
                        modifier = Modifier.weight(1f)
                    )
                    MyButton(
                        text = R.string.cancel_booking,
                        onClick = {
                            isOpen = false
                            cancelBooking(details.bookingNumber)
                        },
                        modifier = Modifier
                            .weight(1f),
                        color = ButtonDefaults.buttonColors(
                            containerColor = if (isDark) darkErrorColor else lightErrorColor
                        )
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SubScreenTopBar(
            title = context.getString(R.string.cancel_playground_booking, details.playgroundName),
            onBackClick = onBackClick,
        )
        BookingCard(
            bookingDetails = details,
            bookingStatus = BookingStatus.CONFIRMED,
            onViewPlaygroundClick = { details.playgroundId },
            modifier = Modifier.padding(
                top = 16.dp,
                end = 16.dp,
                start = 16.dp
            ),
            toRate = {},
            reBook = {}
        )
        Spacer(modifier = Modifier.weight(1f))
        MyButton(
            text = R.string.cancel_booking,
            onClick = {
                isOpen = true
            },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = ButtonDefaults.buttonColors(
                containerColor = if (isDark) darkErrorColor else lightErrorColor
            )
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
