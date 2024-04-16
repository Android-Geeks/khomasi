package com.company.khomasi.presentation.booking

import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.company.khomasi.R
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.theme.darkOverlay
import com.company.khomasi.theme.lightOverlay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import kotlin.math.absoluteValue

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarSlider(modifier: Modifier = Modifier) {
    val bookingViewModel: BookingViewModel = viewModel()
    val bookingUiState: BookingUiState = bookingViewModel.bookingUiState.collectAsState().value

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Column(
            modifier = modifier.padding(start = 16.dp),
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

            CalendarPager()
        }
        Spacer(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 12.dp)
                .width((getScreenWidth() * 0.92).dp)
                .height(1.dp)
                .border(
                    width = 1.dp,
                    color = if (isSystemInDarkTheme()) darkOverlay else lightOverlay
                )
        )

        Text(
            text = stringResource(id = R.string.duration),
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center

        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = { bookingViewModel.UpdateDuration("+") }) {
                Icon(
                    painter = painterResource(R.drawable.pluscircle),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                text = "${bookingUiState.duration} min",
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.tertiary,
            )
            IconButton(
                onClick = { bookingViewModel.UpdateDuration("-") },
                enabled = bookingUiState.duration > 60,
            ) {
                Icon(
                    painter = painterResource(R.drawable.minuscircle),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = if (bookingUiState.duration > 60) 1f else 0.5f),
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .border(width = 1.dp, color = MaterialTheme.colorScheme.outline)
        )

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarPager() {
    val coroutineScope = rememberCoroutineScope()

    val screenWidth = getScreenWidth()

    val currentDate = LocalDate.now()
    val currentDay = currentDate.dayOfMonth
    val currentDaysList = remember {
        (0..20).map { day -> (currentDate).plusDays(day.toLong()) }
    }
    val selectedMonth = remember { mutableStateOf(currentDaysList[currentDay].month) }
    val selectedYear = remember { mutableIntStateOf(currentDate.year) }

    val pagerState = rememberPagerState(pageCount = { currentDaysList.size }, initialPage = 0)
    LaunchedEffect(pagerState.currentPage) {
        selectedMonth.value = currentDaysList[pagerState.currentPage].month
        selectedYear.intValue = currentDaysList[pagerState.currentPage].year
    }
    Text(
        text = "${
            selectedMonth.value.getDisplayName(
                TextStyle.FULL, Locale.getDefault()
            )
        } ${selectedYear.intValue}",
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Start,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(4.dp))

    HorizontalPager(
        state = pagerState,
        pageSize = PageSize.Fixed(60.dp),

        pageSpacing = if (pagerState.currentPage == 0) (-2).dp else (0).dp,
        contentPadding = PaddingValues(start = (screenWidth / 2).dp),
        snapPosition = if (pagerState.currentPage in 0..2) SnapPosition.Start else SnapPosition.Center,
    ) { page ->

        val dayNum = currentDaysList[page].dayOfMonth.toString()
        val dayName = currentDaysList[page].dayOfWeek.getDisplayName(
            TextStyle.SHORT, Locale.getDefault()
        )
        Log.d("selectedDay", "${pagerState.currentPage + currentDay}")

        Card(
            modifier = Modifier
                .clickable { // Add clickable modifier to the Card
                    coroutineScope.launch { // Launch a coroutine
                        pagerState.animateScrollToPage(page) // Scroll to the clicked page
                    }
                }
                .graphicsLayer {
                    // Calculate the absolute offset for the current page from the \scroll position. We use the absolute value which allows us to mirror\ any effects for both directions
                    val pageOffset =
                        ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

                    // We animate the alpha, between 50% and 100%
                    alpha = lerp(
                        start = 0.3f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                    // Scale the size of the page based on its distance from the current page \ The current page will have a scale of 1 (original size), and other pages will have a smaller scale
                    scaleY = lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                    scaleX = lerp(
                        start = 0.9f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                },
            colors = CardDefaults.cardColors(Color.Transparent),
            elevation = CardDefaults.cardElevation(if (page == pagerState.currentPage) 18.dp else (-10).dp)
        ) {
            CalendarItem(
                dayNum = dayNum,
                dayName = dayName
            )
        }
    }
}

@Composable
fun CalendarItem(
    dayNum: String, dayName: String,
) {
    Card(
        modifier = Modifier
            .width(60.dp)
            .height(74.dp),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = dayNum,
                color = MaterialTheme.colorScheme.background,
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = dayName,
                color = MaterialTheme.colorScheme.background,
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun getScreenWidth(): Float {
    val displayMetrics: DisplayMetrics =
        androidx.compose.ui.platform.LocalContext.current.resources.displayMetrics
    return displayMetrics.widthPixels / displayMetrics.density
}

/*@Composable
fun UnFocusedCalendarItem(
    dayNum: String, dayName: String
) {
    Card(
        modifier = Modifier
            .padding(2.dp)
            .width(60.dp)
            .height(62.dp),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(Color(0x8010CE77))

    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = dayNum,
                color = MaterialTheme.colorScheme.background,
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = dayName,
                color = MaterialTheme.colorScheme.background,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
            )
        }
    }

}*/

@Preview(showSystemUi = true, locale = "ar")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Po() {
    KhomasiTheme { CalendarSlider() }
}