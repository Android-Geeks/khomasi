package com.company.khomasi.presentation.myBookings

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.PlaygroundPicture
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.cards.BookingCard
import com.company.khomasi.presentation.components.cards.BookingStatus
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun MyTabRow(pagerState: PagerState) {
    val pagerState = rememberPagerState()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        divider = { Spacer(modifier = Modifier.height(5.dp)) },
        indicator = {
            SecondaryIndicator(
                // modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                height = 5.dp,
                color = MaterialTheme.colorScheme.primary
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

    }


}
@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun MyBookingPage(
    uiState: StateFlow<MyBookingUiState>,
    playgroundPicture: StateFlow<DataState<PlaygroundPicture>>,
) {
    val playgroundPic by playgroundPicture.collectAsState()

    val pagerState = PagerState { 0;" ";2 }

    HorizontalPager(state = pagerState) { index ->
        when (index) {
            0 -> {
                ExpiredPage(
                    uiState,
                    playgroundPicture = playgroundPicture
                )
            }

            1 -> {
                CurrentPage(uiState, playgroundPicture = playgroundPicture)
            }

        }
    }
}
@Composable
fun ExpiredPage(
    uiState: StateFlow<MyBookingUiState>,
    playgroundPicture: StateFlow<DataState<PlaygroundPicture>>,
){

    val currentState = uiState.collectAsState().value
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp),
            color = MaterialTheme.colorScheme.background,
        ) {
            if (currentState.bookingPlayground.isNotEmpty()) {
                        LazyColumn(
                            contentPadding = it,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(currentState.bookingPlayground) {
                                BookingCard(
                                    bookingDetails = it,
                                    playgroundPicture = playgroundPicture,
                                    bookingStatus = BookingStatus.CONFIRMED)
                            }
                        }
            }
        }
    }
}

@Composable
fun CurrentPage(
    uiState: StateFlow<MyBookingUiState>,
    //myBooking: StateFlow<DataState<MyBookingsResponse>>,
    playgroundPicture: StateFlow<DataState<PlaygroundPicture>>,

    ) {

    val currentState = uiState.collectAsState().value
    // val myBookingState by myBooking.collectAsState()


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp),
            color = MaterialTheme.colorScheme.background,
        ) {
            if (currentState.bookingPlayground.isNotEmpty()) {
                        LazyColumn(
                            contentPadding = it,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(currentState.bookingPlayground) {
                                BookingCard(
                                    bookingDetails = it,
                                    playgroundPicture = playgroundPicture,
                                    bookingStatus = BookingStatus.CONFIRMED)

                            }
                        }
            }
        }
    }
}




@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(vertical = 24.dp, horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.frame2),
            contentDescription = "",
            alignment = Alignment.Center
        )
        Text(
            text = stringResource(R.string.no_favorite_fields),
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.height(108.dp))
        MyButton(
            text = R.string.book_field,
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        )
    }
}


//@Preview
//@Composable
//private fun PlaygroundInfoPreview() {
//    PlaygroundInfo( bookingDetails = BookingDetails(
//        1,
//        1,
//        "Al Zamalek Club",
//        "Nasr City",
//        "1/10/2024",
//        7,
//        50,
//        "2425",
//        false
//    ),
//        playgroundPicture = PlaygroundPicture(
//            1,
//            1,
//            " ",
//            false
//        ),
//      //  myBooking =
//
//}