package com.company.khomasi.presentation.booking

import android.annotation.SuppressLint
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.company.khomasi.R
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.FessTimeSlotsResponse
import com.company.khomasi.presentation.components.connectionStates.ThreeBounce
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.theme.darkOverlay
import com.company.khomasi.theme.darkText
import com.company.khomasi.theme.lightOverlay
import com.company.khomasi.theme.lightText
import java.time.Duration
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookingScreen(
    bookingUiState: BookingUiState,
    freeTimeState: DataState<FessTimeSlotsResponse>,
    onBackClicked: () -> Unit,
    updateDuration: (String) -> Unit,
    getFreeSlots: () -> Unit,
    updateSelectedDay: (Int) -> Unit,
    onTimeSlotClicked: (Pair<LocalDateTime, LocalDateTime>) -> Unit
) {
    Scaffold(
        topBar = {
            BookingTopBar(onBackClicked = { onBackClicked() })
        }

    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background,
        ) {
            LaunchedEffect(bookingUiState.selectedDay) {
                getFreeSlots()
            }
            var showLoading by remember { mutableStateOf(false) }

            LaunchedEffect(freeTimeState) {
                showLoading = freeTimeState is DataState.Loading
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = stringResource(id = R.string.date_and_duration),
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    CalendarPager(updateSelectedDay)
                }

                HorizontalDivider(
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 12.dp)
                        .width((getScreenWidth() * 0.92).dp),
                    thickness = 1.dp,
                    color = if (isSystemInDarkTheme()) darkOverlay else lightOverlay
                )

                Text(
                    text = stringResource(id = R.string.duration),
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center

                )
                Spacer(modifier = Modifier.height(4.dp))

                DurationSelection(updateDuration = updateDuration, bookingUiState.duration)

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(), thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outline
                )
                Log.d("freeTimeState", freeTimeState.toString())

                Text(
                    text = stringResource(id = R.string.available_times),
                    style = MaterialTheme.typography.displayLarge,
                    color = if (isSystemInDarkTheme()) darkText else lightText,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(8.dp))

                if (freeTimeState is DataState.Success) {
                    val freeTimeSlots = freeTimeState.data.freeTimeSlots
                    val hourlyIntervalsList = ArrayList<Pair<LocalDateTime, LocalDateTime>>()

                    freeTimeSlots.forEach { slot ->
                        val startTime = parseTimestamp(slot.start).withMinute(0).withSecond(0)
                        val endTime = parseTimestamp(slot.end).withMinute(0).withSecond(0)
                        val duration = Duration.between(startTime, endTime).toHours()

                        for (i in 0 until duration) {
                            val hourStartTime = startTime.plusHours(i)
                            val hourEndTime = hourStartTime.plusHours(1)

                            hourlyIntervalsList.add(
                                Pair(hourStartTime, hourEndTime)
                            )
                        }
                    }

                    val slotColor =
                        remember { List(hourlyIntervalsList.size) { mutableStateOf(Color.Transparent) } }
                    val primaryColor = MaterialTheme.colorScheme.primary

                    LazyColumn(
                        Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(start = 12.dp, end = 20.dp)
                    ) {
                        items(hourlyIntervalsList.size) { slot ->
                            val slotStart = hourlyIntervalsList[slot].first
                            val slotEnd = hourlyIntervalsList[slot].second
                            SlotItem(
                                slotContent = "${formatTime(slotStart)} _ ${formatTime(slotEnd)}",
                                cardColor = slotColor[slot].value,
                                textColor = if (slotColor[slot].value == Color.Transparent) primaryColor else MaterialTheme.colorScheme.background,
                                onClickSlot = {
                                    slotColor[slot].value =
                                        if (slotColor[slot].value == Color.Transparent) primaryColor else Color.Transparent
                                    onTimeSlotClicked(Pair(slotStart, slotEnd))
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
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
    }
}

@Composable
fun SlotItem(
    slotContent: String,
    cardColor: Color,
    textColor: Color = MaterialTheme.colorScheme.primary,
    onClickSlot: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .border(
                color = MaterialTheme.colorScheme.primary,
                width = 1.dp,
                shape = MaterialTheme.shapes.medium
            )
            .clickable { onClickSlot() },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(cardColor),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = slotContent,
                color = textColor,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
            )
        }
    }
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

@RequiresApi(Build.VERSION_CODES.O)
fun formatTime(localDateTime: LocalDateTime): String {
    return localDateTime.format(DateTimeFormatter.ofPattern("hh:mm a"))
}


@Composable
fun getScreenWidth(): Float {
    val displayMetrics: DisplayMetrics =
        androidx.compose.ui.platform.LocalContext.current.resources.displayMetrics
    return displayMetrics.widthPixels / displayMetrics.density
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingTopBar(onBackClicked: () -> Unit = {}) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            Spacer(modifier = Modifier.width(4.dp))
            TopAppBar(
                title = {
                    Text(
                        text = "حجز ملعب الشهداء",
                        style = MaterialTheme.typography.displayMedium,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                },
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = null,
                        Modifier
                            .size(24.dp)
                            .clickable { onBackClicked() }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.background)
            )
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(), thickness = 1.dp
        )
    }
}

@Preview(showSystemUi = true, locale = "ar")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookingScreenPreview() {
    val mockViewModel: MockBookingViewModel = viewModel()
    KhomasiTheme {
        BookingScreen(
            bookingUiState = mockViewModel.bookingUiState.collectAsState().value,
            freeTimeState = mockViewModel.freeSlotsState.collectAsState().value,
            onBackClicked = {},
            updateDuration = { mockViewModel.updateDuration(it) },
            getFreeSlots = mockViewModel::getTimeSlots,
            updateSelectedDay = { mockViewModel.updateSelectedDay(it) },
            onTimeSlotClicked = { mockViewModel.onSlotClicked(it) }
        )
    }
}