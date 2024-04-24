package com.company.khomasi.presentation.booking

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.company.khomasi.R
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.FessTimeSlotsResponse
import com.company.khomasi.presentation.components.AuthSheet
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.connectionStates.ThreeBounce
import com.company.khomasi.presentation.screenDimensions.getScreenHeight
import com.company.khomasi.presentation.screenDimensions.getScreenWidth
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.theme.darkOverlay
import com.company.khomasi.theme.darkText
import com.company.khomasi.theme.lightOverlay
import com.company.khomasi.theme.lightText
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "MutableCollectionMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookingScreen(
    bookingUiState: StateFlow<BookingUiState>,
    freeSlotsState: StateFlow<DataState<FessTimeSlotsResponse>>,
    context: Context = LocalContext.current,
    isDark: Boolean = isSystemInDarkTheme(),
    onBackClicked: () -> Unit,
    updateDuration: (String) -> Unit,
    getFreeSlots: () -> Unit,
    updateSelectedDay: (Int) -> Unit,
    onSlotClicked: (Pair<LocalDateTime, LocalDateTime>) -> Unit,
    checkValidity: () -> Boolean,
    onNextClicked: () -> Unit,
    onBackToBookingScreen: () -> Unit
) {

    val bookingState = bookingUiState.collectAsState().value
    val freeSlots = freeSlotsState.collectAsState().value
    val screenHeight = getScreenHeight()
    BackHandler {
        if (bookingState.page == 2) {
            onBackToBookingScreen()
        } else {
            onBackClicked()
        }
    }
    Scaffold(
        topBar = {
            BookingTopBar(
                playgroundName = bookingState.playgroundName,
                onBackClicked = { onBackClicked() },
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
                    when (bookingState.page) {
                        1 -> BookingScreenContent(
                            bookingUiState = bookingState,
                            freeSlotsState = freeSlots,
                            isDark = isDark,
                            updateDuration = updateDuration,
                            getFreeSlots = { getFreeSlots() },
                            updateSelectedDay = updateSelectedDay,
                            onSlotClicked = onSlotClicked,
                            modifier = Modifier.padding(paddingValues)
                        )

                        2 -> ConfirmBookingContent(
                            bookingState = bookingState,
                            modifier = Modifier.padding(paddingValues),
                        )
                    }
                },
                sheetContent = {
                    when (bookingState.page) {
                        1 -> BookingBottomSheet(
                            sheetHeight = (screenHeight * 0.16).dp,
                            playgroundPrice = bookingState.playgroundPrice,
                            isDark = isDark,
                            context = context,
                            onNextClicked = onNextClicked,
                            checkValidity = checkValidity
                        )

                        2 -> ConfirmBookingBottomSheet(
                            sheetHeight = (screenHeight * 0.16).dp,
                            playgroundPrice = bookingState.playgroundPrice,
                            isDark = isDark,
                            context = context,
                            onContinueToPaymentClicked = { onNextClicked() }
                        )
                    }
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookingScreenContent(
    bookingUiState: BookingUiState,
    freeSlotsState: DataState<FessTimeSlotsResponse>,
    isDark: Boolean,
    updateDuration: (String) -> Unit,
    getFreeSlots: () -> Unit,
    updateSelectedDay: (Int) -> Unit,
    onSlotClicked: (Pair<LocalDateTime, LocalDateTime>) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(bookingUiState.selectedDay) {
        getFreeSlots()
    }
    var showLoading by remember { mutableStateOf(false) }
    val hourlyIntervalsList =
        calculateHourlyIntervalsList(freeSlotsState, bookingUiState.selectedDuration)

    LaunchedEffect(freeSlotsState) {
        showLoading = freeSlotsState is DataState.Loading
    }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.padding(top = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(id = R.string.date_and_duration),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
            )

            Spacer(modifier = Modifier.height(8.dp))

            CalendarPager(updateSelectedDay)
        }

        HorizontalDivider(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 12.dp)
                .width((getScreenWidth() * 0.92).dp),
            thickness = 1.dp,
            color = if (isDark) darkOverlay else lightOverlay
        )

        Text(
            text = stringResource(id = R.string.duration),
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center

        )
        Spacer(modifier = Modifier.height(4.dp))

        DurationSelection(
            updateDuration = updateDuration,
            duration = bookingUiState.selectedDuration
        )

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(id = R.string.available_times),
            style = MaterialTheme.typography.displayLarge,
            color = if (isDark) darkText else lightText,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (freeSlotsState is DataState.Success) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(start = 12.dp, end = 20.dp)
            ) {
                items(hourlyIntervalsList.size) { slot ->
                    val slotStart = hourlyIntervalsList[slot].first
                    val slotEnd = hourlyIntervalsList[slot].second
                    val isSelected =
                        remember {
                            mutableStateOf(
                                bookingUiState.selectedSlots.contains(
                                    Pair(
                                        slotStart,
                                        slotEnd
                                    )
                                )
                            )
                        }

                    SlotItem(
                        slotStart = formatTime(slotStart),
                        slotEnd = formatTime(slotEnd),
                        isSelected = isSelected,
                        onClickSlot = { onSlotClicked(Pair(slotStart, slotEnd)) }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    Spacer(modifier = Modifier.height(136.dp))
                }
            }
        }
        if (showLoading) {
            ThreeBounce(
                modifier = Modifier.fillMaxSize(),
                delayBetweenDotsMillis = 50,
                size = DpSize(75.dp, 75.dp)
            )
        }

    }
}

@Composable
fun BookingBottomSheet(
    sheetHeight: Dp,
    playgroundPrice: Int,
    isDark: Boolean,
    context: Context,
    onNextClicked: () -> Unit,
    checkValidity: () -> Boolean
) {
    Column(
        Modifier
            .fillMaxWidth()
            .height(sheetHeight),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(
            text = context.getString(
                R.string.fees_per_hour, playgroundPrice
            ),
            style = MaterialTheme.typography.displayLarge,
            color = if (isDark) darkText else lightText
        )

        MyButton(
            text = R.string.next,
            onClick = {
                if (!checkValidity()) {
                    Toast
                        .makeText(
                            context,
                            R.string.time_slot_validation,
                            Toast.LENGTH_LONG
                        )
                        .show()
                } else {
                    onNextClicked()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.displayLarge
        )

    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun calculateHourlyIntervalsList(
    freeSlots: DataState<FessTimeSlotsResponse>,
    selectedDuration: Int
): List<Pair<LocalDateTime, LocalDateTime>> {
    return if (freeSlots is DataState.Success) {
        freeSlots.data.freeTimeSlots.map { daySlots ->
            val startTime = parseTimestamp(daySlots.start).withMinute(0).withSecond(0)
            val endTime = parseTimestamp(daySlots.end).withMinute(0).withSecond(0)
            val startHour = startTime.hour
            val endHour = endTime.hour
            val startEndDuration = if (endHour > startHour) {
                (endHour - startHour) * 60
            } else {
                if (endHour < startHour) {
                    (24 - startHour + endHour) * 60
                } else {
                    24 * 60
                }
            }
            val slotsCount = startEndDuration / selectedDuration
            List(slotsCount) { i ->
                val hourStartTime = startTime.plusMinutes(i * selectedDuration.toLong())
                val hourEndTime = hourStartTime.plusMinutes(selectedDuration.toLong())
                Pair(hourStartTime, hourEndTime)
            }
        }.flatten()

    } else {
        emptyList()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatTime(localDateTime: LocalDateTime): String {
    return localDateTime.format(DateTimeFormatter.ofPattern("hh:mm a"))
}

@RequiresApi(Build.VERSION_CODES.O)
fun parseTimestamp(timestamp: String): LocalDateTime {
    return try {
        val offsetDateTime = OffsetDateTime.parse(timestamp)
        offsetDateTime.toLocalDateTime()
    } catch (e: Exception) {
        // If parsing fails, assume timestamp is in UTC time
        LocalDateTime.parse(timestamp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingTopBar(
    playgroundName: String, onBackClicked: () -> Unit = {},
    context: Context
) {
    val currentLanguage = Locale.getDefault().language
    Column(verticalArrangement = Arrangement.Center) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            Spacer(modifier = Modifier.width(4.dp))
            TopAppBar(title = {
                Text(
                    text = context.getString(
                        R.string.booking_playground,
                        playgroundName
                    ),
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
            }, navigationIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = null,
                    Modifier
                        .size(24.dp)
                        .clickable { onBackClicked() }
                        .then(
                            if (currentLanguage == "en") {
                                Modifier.rotate(180f)
                            } else {
                                Modifier
                            }
                        )
                )
            }, colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.background)
            )
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(), thickness = 1.dp
        )
    }
}

@Preview(showSystemUi = true, locale = "en")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookingScreenPreview() {
    val mockViewModel: MockBookingViewModel = viewModel()
    KhomasiTheme {
        BookingScreen(bookingUiState
        = mockViewModel.bookingUiState,
            freeSlotsState = mockViewModel.freeSlotsState,
            onBackClicked = {},
            updateDuration = { mockViewModel.updateDuration(it) },
            getFreeSlots = mockViewModel::getTimeSlots,
            updateSelectedDay = { mockViewModel.updateSelectedDay(it) },
            onSlotClicked = { mockViewModel.onSlotClicked(it) },
            checkValidity = { mockViewModel.checkSlotsConsecutive() },
            onNextClicked = { },
            onBackToBookingScreen = { }
        )
    }
}