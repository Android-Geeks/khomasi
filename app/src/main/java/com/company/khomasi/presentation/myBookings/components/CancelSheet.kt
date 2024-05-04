package com.company.khomasi.presentation.myBookings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.khomasi.R
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.MyModalBottomSheet
import com.company.khomasi.presentation.components.MyOutlinedButton
import com.company.khomasi.presentation.components.cards.BookingCard
import com.company.khomasi.presentation.components.cards.BookingStatus
import com.company.khomasi.presentation.myBookings.MyBookingUiState
import com.company.khomasi.theme.Cairo
import com.company.khomasi.theme.darkIcon
import com.company.khomasi.theme.lightIcon
import kotlinx.coroutines.flow.StateFlow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CancelSheet(
    uiState: StateFlow<MyBookingUiState>,
    onBackClick: () -> Unit,
    cancelBooking: (Int) -> Unit,
) {
    val details = uiState.collectAsState().value.cancelBookingDetails
    val sheetState = rememberModalBottomSheetState()
    var isOpen by remember { mutableStateOf(false) }

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
                            )
                            {
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
                    horizontalArrangement = Arrangement.End
                ) {
                    MyOutlinedButton(
                        onClick = { isOpen = false }, text = R.string.back,
                        modifier = Modifier.weight(1f)
                    )
                    MyButton(
                        onClick = {
                            isOpen = false
                            cancelBooking(details.bookingNumber)
                        },
                        text = R.string.cancel_booking,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .weight(1f)
                    )
                }
            }
        }
    }

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),

            ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
            ) {
//                if (details is DataState.Success) {
//                    val details = details.data
                    TopAppBar(
                        title = {
                            Text(
                                text = details.playgroundName,
                                style = MaterialTheme.typography.displayMedium,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .wrapContentSize(Alignment.CenterStart)
                                    .padding(it)
                            )

                        },
                        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.background),
                        navigationIcon = {
                            IconButton(
                                onClick = onBackClick,
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.back),
                                    modifier = if (LocalLayoutDirection.current == LayoutDirection.Ltr) Modifier.rotate(
                                        180f
                                    ) else Modifier,
                                    contentDescription = null,
                                    tint = if (isSystemInDarkTheme()) darkIcon else lightIcon
                                )
                            }
                        }
                    )
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(), thickness = 1.dp
                    )
                    BookingCard(
                        bookingDetails = details,
                        bookingStatus = BookingStatus.CONFIRMED,
                        onViewPlaygroundClick = { details.playgroundId },
                        toRate = {},
                        reBook = {}
                    )
                //   }
                Spacer(modifier = Modifier.height(141.dp))
                MyButton(
                    text = R.string.booking_cancelled,
                    onClick = {
                        isOpen = true
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(56.dp))
            }
        }
    }
