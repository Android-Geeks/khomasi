package com.company.rentafield.presentation.screens.booking

import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.rentafield.R
import com.company.rentafield.domain.DataState
import com.company.rentafield.presentation.components.AuthSheet
import com.company.rentafield.presentation.components.MyButton
import com.company.rentafield.presentation.screens.booking.components.CalendarPager
import com.company.rentafield.presentation.screens.booking.components.DurationSelection
import com.company.rentafield.presentation.screens.booking.components.SlotItem
import com.company.rentafield.presentation.theme.RentafieldTheme
import com.company.rentafield.presentation.theme.darkOverlay
import com.company.rentafield.presentation.theme.lightOverlay
import com.company.rentafield.utils.extractTimeFromTimestamp
import com.company.rentafield.utils.screenDimensions.getScreenHeight
import com.company.rentafield.utils.screenDimensions.getScreenWidth
import kotlinx.coroutines.flow.StateFlow
import org.threeten.bp.LocalDateTime
import java.util.Locale


@Composable
fun BookingScreen(
    bookingUiState: StateFlow<BookingUiState>,
    playgroundId: Int,
    freeSlotsState: StateFlow<DataState<com.company.rentafield.domain.models.playground.FreeTimeSlotsResponse>>,
    context: Context = LocalContext.current,
    isDark: Boolean = isSystemInDarkTheme(),
    onBackClicked: () -> Unit,
    updateDuration: (String) -> Unit,
    getPlaygroundDetails: (Int) -> Unit,
    getFreeSlots: () -> Unit,
    updateSelectedDay: (Int) -> Unit,
    onSlotClicked: (Pair<LocalDateTime, LocalDateTime>) -> Unit,
    checkValidity: () -> Boolean,
    onNextToConfirmationClicked: () -> Unit,
) {
    val bookingState by bookingUiState.collectAsStateWithLifecycle()
    val freeSlots by freeSlotsState.collectAsStateWithLifecycle()
    val screenHeight = getScreenHeight(context)

    LaunchedEffect(Unit) {
        getPlaygroundDetails(playgroundId)
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
                    BookingScreenContent(
                        bookingUiState = bookingState,
                        freeSlotsState = freeSlots,
                        isDark = isDark,
                        context = context,
                        hourlyIntervalsList = bookingState.hourlyIntervalList,
                        updateDuration = updateDuration,
                        getFreeSlots = { getFreeSlots() },
                        updateSelectedDay = updateSelectedDay,
                        onSlotClicked = onSlotClicked,
                        modifier = Modifier.padding(paddingValues),
                    )
                },
                sheetContent = {
                    BookingBottomSheet(
                        sheetHeight = (screenHeight * 0.16).dp,
                        playgroundPrice = bookingState.totalPrice,
                        context = context, onNextClicked = onNextToConfirmationClicked,
                        checkValidity = checkValidity
                    )
                }
            )
        }
    }
}

@Composable
fun BookingScreenContent(
    bookingUiState: BookingUiState,
    freeSlotsState: DataState<com.company.rentafield.domain.models.playground.FreeTimeSlotsResponse>,
    hourlyIntervalsList: List<Pair<LocalDateTime, LocalDateTime>>,
    isDark: Boolean,
    context: Context,
    updateDuration: (String) -> Unit,
    getFreeSlots: () -> Unit,
    updateSelectedDay: (Int) -> Unit,
    onSlotClicked: (Pair<LocalDateTime, LocalDateTime>) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(bookingUiState.selectedDay, bookingUiState.playgroundName) {
        getFreeSlots()
    }
//    val showLoading by remember { mutableStateOf(false) }

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
                .width((getScreenWidth(context) * 0.92).dp),
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
            color = MaterialTheme.colorScheme.onPrimaryContainer,
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
                        bookingUiState.selectedSlots.contains(
                            Pair(
                                slotStart,
                                slotEnd
                            )
                        )


                    SlotItem(
                        slotStart = extractTimeFromTimestamp(slotStart),
                        slotEnd = extractTimeFromTimestamp(slotEnd),
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
    }

}

@Composable
fun BookingBottomSheet(
    sheetHeight: Dp,
    playgroundPrice: Int,
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
            text = "$playgroundPrice" + " " + stringResource(R.string.price_egp),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer
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
                IconButton(onClick = { onBackClicked() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = null,
                        Modifier
                            .size(24.dp)
                            .then(
                                if (currentLanguage == "en") {
                                    Modifier.rotate(180f)
                                } else {
                                    Modifier
                                }
                            )
                    )
                }
            }, colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.background)
            )
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(), thickness = 1.dp
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
fun BookingScreenPreview() {
    val mockViewModel = MockBookingViewModel()
    RentafieldTheme {
        BookingScreen(
            bookingUiState
            = mockViewModel.bookingUiState,
            freeSlotsState = mockViewModel.freeSlotsState,
            onBackClicked = {},
            updateDuration = { mockViewModel.updateDuration(it) },
            getFreeSlots = mockViewModel::getTimeSlots,
            updateSelectedDay = { mockViewModel.updateSelectedDay(it) },
            onSlotClicked = { mockViewModel.onSlotClicked(it) },
            checkValidity = { mockViewModel.checkSlotsConsecutive() },
            onNextToConfirmationClicked = { },
            playgroundId = 1,
            getPlaygroundDetails = { _ -> }
        )
    }
}