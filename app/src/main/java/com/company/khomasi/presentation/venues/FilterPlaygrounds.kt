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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.company.khomasi.theme.darkText
import com.company.khomasi.theme.lightOverlay
import com.company.khomasi.theme.lightText
import kotlinx.coroutines.flow.StateFlow

@Composable
fun FilterPlaygrounds(
    filteredUiState: StateFlow<BrowseUiState>,
    onBackClick: () -> Unit,
    isDark: Boolean = isSystemInDarkTheme(),
    onClickSelectDate: () -> Unit,
    onClickStartTime: () -> Unit,
    updateDuration: (String) -> Unit,
) {
    val screenWidth = getScreenWidth()
    val screenHeight = getScreenHeight()
    val uiState by filteredUiState.collectAsStateWithLifecycle()

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
                    content = R.string.price_per_hour, isDark = isDark
                )

                PriceSlider(initValue = 50f, maxValue = 150f)
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
                    content = R.string.date_and_time, isDark = isDark
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
                        text = "10-10-2024",
                        isDark = isDark,
                        modifier = Modifier.weight(1f),
                        onClickText = onClickSelectDate
                    )

                    VerticalDivider(
                        modifier = Modifier
                            .height(screenHeight.dp * 0.15f),
                        color = if (isDark) darkOverlay else lightOverlay,
                        thickness = 1.dp
                    )

                    ColumnWithText(
                        header = R.string.start_time,
                        isDark = isDark,
                        text = "10:00 ص",
                        modifier = Modifier.weight(1f),
                        onClickText = onClickStartTime
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
        }
    }
}

@Composable
fun HeaderText(
    @StringRes content: Int,
    isDark: Boolean,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = content),
        color = if (isDark) darkText else lightText,
        style = MaterialTheme.typography.displayMedium,
        modifier = modifier,
        textAlign = TextAlign.Start
    )
}

@Composable
fun ColumnWithText(
    @StringRes header: Int,
    text: String,
    isDark: Boolean,
    modifier: Modifier = Modifier,
    onClickText: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(top = 30.dp, bottom = 45.dp)
    ) {
        HeaderText(
            content = header, isDark = isDark,
        )
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

@Preview(showSystemUi = true, locale = "ar")
@Composable
fun FilteredPlaygroundPreview() {
    val mockViewModel = MockBrowseViewModel()
    KhomasiTheme {
        FilterPlaygrounds(
            filteredUiState = mockViewModel.uiState,
            onBackClick = {},
            onClickSelectDate = {},
            onClickStartTime = {},
            updateDuration = {}
        )
    }
}