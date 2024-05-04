package com.company.khomasi.presentation.venues

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.khomasi.R
import com.company.khomasi.presentation.components.SubScreenTopBar
import com.company.khomasi.presentation.playground.components.DurationSelection
import com.company.khomasi.presentation.screenDimensions.getScreenHeight
import com.company.khomasi.presentation.screenDimensions.getScreenWidth
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.theme.darkOverlay
import com.company.khomasi.theme.lightOverlay
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterPlaygrounds(
    filteredUiState: StateFlow<BrowseUiState>,
    onBackClick: () -> Unit,
    isDark: Boolean = isSystemInDarkTheme(),
    onShowFiltersClicked: (SelectedFilter, String) -> Unit,
    onResetFilters: () -> Unit,
    updateDuration: (String) -> Unit,
    setPrice: (Int) -> Unit
) {
    val screenWidth = getScreenWidth()
    val screenHeight = getScreenHeight()
    val uiState by filteredUiState.collectAsStateWithLifecycle()

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
            DateTimeFormatter.ofPattern("yyyy-MM-dd")
                .format(
                    pickedDate.time.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                )
        }
    }
//--------------------------------------------------------------------------

    val timePickerState = rememberTimePickerState(
        initialHour = LocalTime.NOON.hour,
        initialMinute = 30,
        is24Hour = false
    )
    val pickedTime =
        "${timePickerState.hour}:${timePickerState.minute}"

    var showTimePicker by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            SubScreenTopBar(
                title = R.string.browse_results,
                onBackClick = onBackClick,
            )

        }
    ) { paddingValues ->
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
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                HeaderText(
                    content = R.string.price_per_hour,
                )

                PriceSlider(initValue = 50f, maxValue = 150f, setPrice = setPrice)
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
                    ColumnWithText(
                        header = R.string.select_date,
                        text = formattedDate ?: "2024-10-10",
                        modifier = Modifier.weight(1f),
                        onClickText = { showDatePicker = true }
                    )

                    VerticalDivider(
                        modifier = Modifier
                            .height(screenHeight.dp * 0.15f),
                        color = if (isDark) darkOverlay else lightOverlay,
                        thickness = 1.dp
                    )
                    ColumnWithText(
                        header = R.string.start_time,
                        text = "${timePickerState.hour}:${timePickerState.minute}",
                        modifier = Modifier.weight(1f),
                        onClickText = { showTimePicker = true }
                    )

                }

            }
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(
                modifier = Modifier.width(screenWidth.dp * 0.66f),
                thickness = 1.dp,
                color = if (isDark) darkOverlay else lightOverlay
            )
            DurationSelection(
                updateDuration = updateDuration,
                duration = uiState.selectedDuration,
                modifier = Modifier.padding(vertical = 30.dp)
            )
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = if (isDark) darkOverlay else lightOverlay
            )
            val choice = remember {
                mutableIntStateOf(0)
            }
            PlaygroundsFilterSelection(
                choice = choice.intValue,
                onChoiceChange = { choice.intValue = it },
                onShowFiltersClicked = onShowFiltersClicked,
                onResetFilters = onResetFilters,
                bookingTime = formattedDate + "T" + pickedTime + ":00"
            )


            if (showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        Button(
                            onClick = {
                                showDatePicker = false

                            },
                            colors = ButtonDefaults.buttonColors(Color.Transparent)
                        ) {
                            Text(
                                text = stringResource(id = R.string.ok),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = { showDatePicker = false },
                            colors = ButtonDefaults.buttonColors(Color.Transparent)
                        ) {
                            Text(
                                text = stringResource(id = R.string.cancel),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    colors = androidx.compose.material3.DatePickerDefaults.colors(
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
                MyTimePickerDialog(
                    onDismissRequest = { },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showTimePicker = false
                            }
                        ) {
                            Text(
                                stringResource(id = R.string.ok),
                                color = MaterialTheme.colorScheme.secondary,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showTimePicker = false
                            }
                        ) {
                            Text(
                                stringResource(R.string.cancel),
                                color = MaterialTheme.colorScheme.secondary,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    }
                ) {
                    TimePicker(
                        state = timePickerState,
                        colors = TimePickerDefaults.colors(
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
        }
    }
}

@Composable
fun HeaderText(
    @StringRes content: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = content),
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        style = MaterialTheme.typography.displayMedium,
        modifier = modifier,
        textAlign = TextAlign.Start
    )
}

@Composable
fun ColumnWithText(
    @StringRes header: Int,
    text: String,
    modifier: Modifier = Modifier,
    onClickText: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(top = 30.dp, bottom = 45.dp)
    ) {
        HeaderText(content = header)
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .padding(top = 4.dp)
                .clickable { onClickText() }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun FilteredPlaygroundPreview() {
    val mockViewModel = MockBrowseViewModel()
    KhomasiTheme {
        FilterPlaygrounds(
            filteredUiState = mockViewModel.uiState,
            onBackClick = {},
            updateDuration = {},
            onShowFiltersClicked = { _, _ -> },
            setPrice = mockViewModel::setPrice,
            onResetFilters = mockViewModel::resetFilteredPlaygrounds
        )
    }
}