package com.company.khomasi.presentation.myBookings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TabRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.company.khomasi.R
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.MyBookingsResponse
import com.company.khomasi.presentation.myBookings.components.TabItem
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun MyBookingPage(
    uiState: StateFlow<MyBookingUiState>,
    myBooking: StateFlow<DataState<MyBookingsResponse>>,
    onClickPlaygroundCard: (Int) -> Unit,
    myBookingPlaygrounds: () -> Unit,
    onBackClick: () -> Unit
) {
    val bookingUiState = uiState.collectAsState().value
    val myBooking = myBooking.collectAsState().value
    LaunchedEffect(key1 = Unit) {
        myBookingPlaygrounds()
    }

    val list = listOf(TabItem.Current, TabItem.Expired)
    val pagerState = rememberPagerState(initialPage = 0)
    Column(modifier = Modifier.fillMaxSize()) {
        Tabs(tabs = list, pagerState = pagerState)
        Image(
            painter = painterResource(R.drawable.view_pager_group), contentDescription = null,
            contentScale = ContentScale.FillWidth, modifier = Modifier.fillMaxWidth()
        )
        TabContent(
            tabs = list,
            pagerState = pagerState,
            uiState = bookingUiState,
            onClickPlaygroundCard = onClickPlaygroundCard,
            onBackClick = onBackClick
        )
    }
}
@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(tabs: List<TabItem>, pagerState: PagerState) {
    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.background,
        indicator = { tabPositions ->
            SecondaryIndicator(
                modifier = Modifier
                    .pagerTabIndicatorOffset(pagerState, tabPositions)
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.background
            )
        }
    ) {
        tabs.forEachIndexed { index, tabItem ->

            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = {
                    Text(
                        text = stringResource(id = tabItem.title),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.tertiary,
                enabled = true
            )

        }

    }
}
@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabContent(
    tabs: List<TabItem>,
    pagerState: PagerState,
    uiState: MyBookingUiState,
    onClickPlaygroundCard: (Int) -> Unit,
    onBackClick: () -> Unit

) {
    HorizontalPager(count = tabs.size, state = pagerState) { page ->

        tabs[page].screens(uiState, onClickPlaygroundCard, onBackClick)
    }
}

