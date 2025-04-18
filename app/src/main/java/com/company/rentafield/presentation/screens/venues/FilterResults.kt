package com.company.rentafield.presentation.screens.venues

import android.annotation.SuppressLint
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.rentafield.R
import com.company.rentafield.presentation.components.SubScreenTopBar
import com.company.rentafield.presentation.screens.booking.components.DurationSelection
import com.company.rentafield.presentation.screens.venues.component.BottomSheetWarning
import com.company.rentafield.presentation.screens.venues.component.MyTimePickerDialog
import com.company.rentafield.presentation.screens.venues.component.PlaygroundsFilterSelection
import com.company.rentafield.presentation.screens.venues.component.PriceSlider
import com.company.rentafield.presentation.screens.venues.component.TypeCard
import com.company.rentafield.presentation.theme.RentafieldTheme
import com.company.rentafield.presentation.theme.darkOverlay
import com.company.rentafield.presentation.theme.lightOverlay
import com.company.rentafield.utils.screenDimensions.getScreenHeight
import com.company.rentafield.utils.screenDimensions.getScreenWidth
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Calendar

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterResults(
    filteredUiState: StateFlow<BrowseUiState>,
    onBackClick: () -> Unit,
    context: Context = LocalContext.current,
    isDark: Boolean = isSystemInDarkTheme(),
    onShowFiltersClicked: (SelectedFilter, String) -> Unit,
    onResetFilters: () -> Unit,
    updateDuration: (String) -> Unit,
    setPrice: (Int) -> Unit,
    setBookingTime: (String) -> Unit,
    updateType: (Int) -> Unit
) {
    BackHandler {
        onBackClick()
    }
    val uiState by filteredUiState.collectAsStateWithLifecycle()
    val screenWidth = getScreenWidth(context)
    val screenHeight = getScreenHeight(context)
    val sheetState = rememberModalBottomSheetState()
    var showResetSheet by remember { mutableStateOf(false) }

    val date = remember {
        Calendar.getInstance().apply {
            set(Calendar.YEAR, LocalDate.now().year)
            set(Calendar.MONTH, LocalDate.now().monthValue - 1)
            set(Calendar.DAY_OF_MONTH, LocalDate.now().dayOfMonth)
        }.timeInMillis
    }
    val currentDayMillis =
        remember { LocalDate.now().atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli() }

    val selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis >= currentDayMillis
        }
    }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = date,
        yearRange = LocalDate.now().year..LocalDate.now().year + 1,
        selectableDates = selectableDates
    )
    var showDatePicker by remember { mutableStateOf(false) }

    var pickedDate by remember {
        mutableStateOf(Calendar.getInstance())
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("yyyy-MM-dd").format(
                pickedDate.time.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            )
        }
    }
//--------------------------------------------------------------------------

    val timePickerState = rememberTimePickerState(
        initialHour = LocalTime.NOON.hour, initialMinute = 30, is24Hour = false
    )

    val pickedTime = "${
        if (timePickerState.hour < 10) {
            "0" + timePickerState.hour.toString()
        } else {
            timePickerState.hour.toString()
        }
    }:${
        if (timePickerState.minute < 10) {
            "0" + timePickerState.minute.toString()
        } else {
            timePickerState.minute.toString()
        }
    }"

    var showTimePicker by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        SubScreenTopBar(
            title = stringResource(id = R.string.filter_results),
            onBackClick = onBackClick,
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.spacedBy((-10).dp)
            ) {
                HeaderText(
                    content = R.string.price_per_hour,
                )
                PriceSlider(
                    filteredUiState = filteredUiState, setPrice = setPrice
                )
            }
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outline
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                HeaderText(
                    content = R.string.date_and_time,
                )
                Spacer(modifier = Modifier.height(8.dp))

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = if (isDark) darkOverlay else lightOverlay
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Top
                ) {
                    ColumnWithText(header = R.string.select_date,
                        text = uiState.bookingTime.split('T')[0],
                        modifier = Modifier.weight(1f),
                        onClickText = { showDatePicker = true })

                    VerticalDivider(
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .height(screenHeight.dp * 0.12f),
                        color = if (isDark) darkOverlay else lightOverlay,
                        thickness = 1.dp
                    )
                    ColumnWithText(header = R.string.start_time,
                        text = uiState.bookingTime.split('T')[1].substring(0, 5),
                        modifier = Modifier.weight(1f),
                        onClickText = { showTimePicker = true })

                }
            }
            HorizontalDivider(
                modifier = Modifier.width(screenWidth.dp * 0.66f),
                thickness = 1.dp,
                color = if (isDark) darkOverlay else lightOverlay
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.duration),
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
            DurationSelection(
                updateDuration = updateDuration,
                duration = uiState.selectedDuration,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outline
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                HeaderText(content = R.string.field_size)

                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    items(uiState.listOfTypes) { type ->
                        TypeCard(
                            type = type, onClickCard = {
                                uiState.selectedType.apply {
                                    if (contains(type)) {
                                        remove(type)
                                    } else {
                                        uiState.selectedType.clear()
                                        add(type)
                                        updateType(type)
                                    }
                                }
                            },
                            isSelected = mutableStateOf(uiState.selectedType.contains(type))
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outline
            )
            PlaygroundsFilterSelection(
                choice = uiState.choice.intValue,
                onChoiceChange = { uiState.choice.intValue = it },
                onShowFiltersClicked = onShowFiltersClicked,
                onResetFilters = { showResetSheet = true },
                bookingTime = uiState.bookingTime
            )

            if (showDatePicker) {
                DatePickerDialog(onDismissRequest = { showDatePicker = false }, confirmButton = {
                    MyDialogButton(text = stringResource(id = R.string.ok), onClick = {
                        showDatePicker = false
                        setBookingTime(formattedDate + "T" + pickedTime + ":00")
                    })
                }, dismissButton = {
                    MyDialogButton(text = stringResource(id = R.string.cancel),
                        onClick = { showDatePicker = false })
                }, colors = androidx.compose.material3.DatePickerDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surface,
                )
                ) {
                    DatePicker(
                        state = datePickerState,
                    )
                    pickedDate = Calendar.getInstance().apply {
                        timeInMillis = datePickerState.selectedDateMillis!!
                    }
                }


            }

            if (showTimePicker) {
                MyTimePickerDialog(title = stringResource(id = R.string.start_time),
                    onDismissRequest = { showTimePicker = false },
                    confirmButton = {
                        MyDialogButton(text = stringResource(id = R.string.ok), onClick = {
                            showTimePicker = false
                            setBookingTime(formattedDate + "T" + pickedTime + ":00")
                        })
                    },
                    dismissButton = {
                        MyDialogButton(text = stringResource(id = R.string.cancel),
                            onClick = { showTimePicker = false })
                    }) {
                    TimePicker(
                        state = timePickerState, colors = TimePickerDefaults.colors(
                            clockDialColor = MaterialTheme.colorScheme.tertiary.copy(0.03f),
                            clockDialSelectedContentColor = MaterialTheme.colorScheme.background,
                            clockDialUnselectedContentColor = MaterialTheme.colorScheme.outline,
                            selectorColor = MaterialTheme.colorScheme.secondary,
                            containerColor = MaterialTheme.colorScheme.background,
                            periodSelectorBorderColor = Color.Unspecified,
                            periodSelectorSelectedContainerColor = MaterialTheme.colorScheme.primary.copy(
                                0.8f
                            ),
                            periodSelectorUnselectedContainerColor = Color.Unspecified,
                            periodSelectorSelectedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            periodSelectorUnselectedContentColor = Color.Unspecified,
                            timeSelectorSelectedContainerColor = MaterialTheme.colorScheme.primary.copy(
                                0.8f
                            ),
                            timeSelectorUnselectedContainerColor = MaterialTheme.colorScheme.tertiary.copy(
                                0.03f
                            ),
                            timeSelectorSelectedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            timeSelectorUnselectedContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                }
            }

            if (showResetSheet) {
                BottomSheetWarning(sheetState = sheetState,
                    hideSheet = { showResetSheet = false },
                    onClickReset = {
                        onResetFilters()
                    })
            }
        }
    }
}


@Composable
fun MyDialogButton(
    text: String,
    onClick: () -> Unit,
) {
    TextButton(
        onClick = onClick
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun HeaderText(
    @StringRes content: Int, modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
) {
    Text(
        text = stringResource(id = content),
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        style = MaterialTheme.typography.displayMedium,
        textAlign = textAlign,
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun ColumnWithText(
    @StringRes header: Int, text: String, modifier: Modifier = Modifier, onClickText: () -> Unit
) {
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickable { onClickText() }
            .padding(top = 10.dp, bottom = 15.dp)) {
        HeaderText(
            content = header, textAlign = TextAlign.Center,
        )
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.tertiary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
    }

}

@Preview(showSystemUi = true)
@Composable
fun FilteredPlaygroundPreview() {
    val mockViewModel = MockBrowseViewModel()
    RentafieldTheme {
        FilterResults(filteredUiState = mockViewModel.uiState,
            onBackClick = {},
            updateDuration = {},
            onShowFiltersClicked = { _, _ -> },
            setPrice = { _ -> },
            onResetFilters = { },
            setBookingTime = {},
            updateType = {})
    }
}