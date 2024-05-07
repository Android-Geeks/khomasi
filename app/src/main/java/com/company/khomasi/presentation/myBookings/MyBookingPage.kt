package com.company.khomasi.presentation.myBookings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.company.khomasi.R
import com.company.khomasi.domain.model.BookingDetails
import com.company.khomasi.presentation.myBookings.components.TabItem
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun MyBookingPage(
    uiState: StateFlow<MyBookingUiState>,
    onClickPlaygroundCard: (BookingDetails) -> Unit,
    myBookingPlaygrounds: () -> Unit,
    playgroundReview: () -> Unit,
    onCommentChange: (String) -> Unit,
    onRatingChange: (Float) -> Unit,
    reBook: (Int) -> Unit,
    onClickBookField: () -> Unit,
    cancelDetails: (Int) -> Unit
) {
    LaunchedEffect(Unit) {
        myBookingPlaygrounds()
    }
    val list = listOf(TabItem.Current, TabItem.Expired)
    val pagerState = rememberPagerState(initialPage = 0)
    Column(modifier = Modifier.fillMaxSize()) {
        Tabs(tabs = list, pagerState = pagerState)
        Image(
            painter = painterResource(R.drawable.view_pager_group),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )
        TabContent(
            tabs = list,
            pagerState = pagerState,
            uiState = uiState,
            onClickPlaygroundCard = onClickPlaygroundCard,
            playgroundReview = playgroundReview,
            onCommentChange = onCommentChange,
            onRatingChange = onRatingChange,
            reBook = reBook,
            onClickBookField = onClickBookField,
            cancelDetails = cancelDetails
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(tabs: List<TabItem>, pagerState: PagerState) {
    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.background,
        indicator = @Composable {},
        divider = @Composable {},
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
    uiState: StateFlow<MyBookingUiState>,
    onClickPlaygroundCard: (BookingDetails) -> Unit,
    playgroundReview: () -> Unit,
    onCommentChange: (String) -> Unit,
    onRatingChange: (Float) -> Unit,
    onClickBookField: () -> Unit,
    reBook: (Int) -> Unit,
    cancelDetails: (Int) -> Unit
) {
    HorizontalPager(count = tabs.size, state = pagerState) { page ->
        tabs[page].screens(
            uiState,
            onClickPlaygroundCard,
            playgroundReview,
            onCommentChange,
            onRatingChange,
            reBook,
            onClickBookField,
            cancelDetails
        )
    }
}

