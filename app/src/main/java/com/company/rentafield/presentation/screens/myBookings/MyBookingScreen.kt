package com.company.rentafield.presentation.screens.myBookings

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.ui.tooling.preview.Preview
import com.company.rentafield.R
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.MessageResponse
import com.company.rentafield.domain.model.booking.BookingDetails
import com.company.rentafield.domain.model.booking.MyBookingsResponse
import com.company.rentafield.presentation.screens.myBookings.components.CurrentPage
import com.company.rentafield.presentation.screens.myBookings.components.ExpiredPage
import com.company.rentafield.presentation.theme.RentafieldTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun MyBookingScreen(
    uiState: StateFlow<MyBookingUiState>,
    myBookingsState: StateFlow<DataState<MyBookingsResponse>>,
    reviewState: StateFlow<DataState<MessageResponse>>,
    onClickPlaygroundCard: (BookingDetails) -> Unit,
    myBookingPlaygrounds: () -> Unit,
    playgroundReview: () -> Unit,
    onCommentChange: (String) -> Unit,
    onRatingChange: (Float) -> Unit,
    reBook: (Int, Boolean) -> Unit,
    onClickBookField: () -> Unit,
    cancelDetails: (Int, Boolean) -> Unit,
) {
    LaunchedEffect(Unit) {
        myBookingPlaygrounds()
    }
    val pagerState = rememberPagerState(initialPage = 0)
    val pagerItems = listOf(R.string.current, R.string.expired)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Tabs(tabs = pagerItems, pagerState = pagerState)
        Image(
            painter = painterResource(R.drawable.view_pager_group),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )
        TabContent(
            uiState = uiState,
            myBookingsState = myBookingsState,
            reviewState = reviewState,
            pagerItems = pagerItems,
            pagerState = pagerState,
            onClickPlaygroundCard = onClickPlaygroundCard,
            playgroundReview = playgroundReview,
            onCommentChange = onCommentChange,
            onRatingChange = onRatingChange,
            reBook = reBook,
            onClickBookField = onClickBookField,
            cancelDetails = cancelDetails,
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(tabs: List<Int>, pagerState: PagerState) {
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
                        text = stringResource(id = tabItem),
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
    uiState: StateFlow<MyBookingUiState>,
    myBookingsState: StateFlow<DataState<MyBookingsResponse>>,
    reviewState: StateFlow<DataState<MessageResponse>>,
    pagerState: PagerState,
    pagerItems: List<Int>,
    onClickPlaygroundCard: (BookingDetails) -> Unit,
    playgroundReview: () -> Unit,
    onCommentChange: (String) -> Unit,
    onRatingChange: (Float) -> Unit,
    onClickBookField: () -> Unit,
    reBook: (Int, Boolean) -> Unit,
    cancelDetails: (Int, Boolean) -> Unit,
) {
    HorizontalPager(count = pagerItems.size, state = pagerState) { page ->
        if (page == 0) {
            CurrentPage(
                uiState,
                myBookingsState,
                onClickPlaygroundCard,
                onClickBookField,
                cancelDetails
            )
        } else {
            ExpiredPage(
                uiState,
                myBookingsState,
                reviewState,
                playgroundReview,
                onCommentChange,
                onRatingChange,
                reBook,
                onClickBookField,
            )
        }
    }
}

@Preview(name = "DARK | EN", locale = "en", uiMode = UI_MODE_NIGHT_YES, showSystemUi = true)
@Preview(name = "LIGHT | AR", locale = "ar", uiMode = UI_MODE_NIGHT_NO, showSystemUi = true)
@Composable
fun MyBookingScreenPreview() {
    val myBookingViewModel = MyBookingMockViewModel()
    RentafieldTheme {
        MyBookingScreen(
            uiState = myBookingViewModel.uiState,
            myBookingsState = myBookingViewModel.myBooking,
            reviewState = myBookingViewModel.reviewState,
            onClickPlaygroundCard = { _ -> },
            myBookingPlaygrounds = { },
            playgroundReview = { },
            onCommentChange = { _ -> },
            onRatingChange = { _ -> },
            reBook = { _, _ -> },
            onClickBookField = { },
            cancelDetails = { _, _ -> })
    }
}
