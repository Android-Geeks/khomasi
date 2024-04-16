package com.company.khomasi.presentation.booking

import android.content.res.Resources
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.company.khomasi.R
import com.company.khomasi.theme.KhomasiTheme
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import kotlin.math.absoluteValue


@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarSlider(modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()

    val displayMetrics: DisplayMetrics = Resources.getSystem().displayMetrics
    val screenWidth = displayMetrics.widthPixels / displayMetrics.density

    val currentDate = LocalDate.now()
    val currentDay = currentDate.dayOfMonth
    val currentDaysList = remember {
        (0..20).map { day -> (currentDate).plusDays(day.toLong()) }
    }
    val selectedDay = remember { mutableIntStateOf(0) }
    val selectedMonth = remember { mutableStateOf(currentDaysList[currentDay].month) }
    val selectedYear = remember { mutableIntStateOf(currentDate.year) }
    val myWidth = remember {
        mutableStateOf(150.dp)
    }

    Column(
        modifier = modifier.padding(start = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.date_and_duration),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
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
        }
        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.Start
        ) {
//            Spacer(modifier = Modifier.width(myWidth.value))

            val pagerState =
                rememberPagerState(pageCount = { currentDaysList.size }, initialPage = 0)

            var snapPosition = remember(pagerState.currentPage) {
                if (pagerState.currentPage in 0..2) SnapPosition.Start else SnapPosition.Center
            }

            // Update selectedMonth when the current page changes
            LaunchedEffect(pagerState.currentPage) {
                selectedMonth.value = currentDaysList[pagerState.currentPage].month
                selectedYear.intValue = currentDaysList[pagerState.currentPage].year
                selectedDay.intValue = pagerState.currentPage + currentDay
                if (pagerState.currentPage != 0) {
                    myWidth.value -= 68.dp.coerceAtLeast(0.dp)
                }

                snapPosition =
                    if (pagerState.currentPage in 0..2) SnapPosition.Start else SnapPosition.Center


            }
            HorizontalPager(
                state = pagerState,
                pageSize = PageSize.Fixed(68.dp), pageSpacing = (-8).dp,
                contentPadding = PaddingValues(start = (screenWidth / 2).dp - 58.dp),
                snapPosition = snapPosition,

                ) { page ->
                val dayNum = currentDaysList[page].dayOfMonth
                Log.d("currentDaysList", "size : ${currentDaysList.size}")
                Card(
                    modifier = Modifier
                        .clickable { // Add clickable modifier to the Card
                            coroutineScope.launch { // Launch a coroutine
                                pagerState.animateScrollToPage(page) // Scroll to the clicked page
                            }
                        }
                        .graphicsLayer {
                            // Calculate the absolute offset for the current page from the
                            // scroll position. We use the absolute value which allows us to mirror
                            // any effects for both directions
                            val pageOffset =
                                ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

                            // We animate the alpha, between 50% and 100%
                            alpha = lerp(
                                start = 0.5f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )     // Scale the size of the page based on its distance from the current page
                            // The current page will have a scale of 1 (original size), and other pages will have a smaller scale
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
                        dayNum = dayNum.toString(),
                        dayName = currentDaysList[page].dayOfWeek.getDisplayName(
                            TextStyle.SHORT, Locale.getDefault(),
                        ),

                        )
                }
            }
        }
    }
}

/*        LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    items(currentMonthDays.size) {
                        val day = currentMonthDays[it]
                        val dayName = day.dayOfWeek.getDisplayName(
                            TextStyle.SHORT, Locale.getDefault()
                        )

                        val dayNum = day.dayOfMonth
                        Log.d(
                            "currentMonthDays",
                            " index : $it - curDay ${dayNum - currentDate.dayOfMonth}"
                        )

                        if (dayNum - currentDate.dayOfMonth == it) CalendarItem(
                            dayNum = dayNum.toString(), dayName = dayName
                        )
                        else UnFocusedCalendarItem(dayNum = dayNum.toString(), dayName = dayName)

                    }
                }*/
@Composable
fun CalendarItem(
    dayNum: String, dayName: String,
) {
    Card(
        modifier = Modifier
            .width(58.dp)
            .height(68.dp),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(0.dp)
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

@Preview(showSystemUi = true)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Po() {
    KhomasiTheme { CalendarSlider() }
}