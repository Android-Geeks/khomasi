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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.BookingDetails
import com.company.khomasi.domain.model.MyBookingsResponse
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.cards.BookingCard
import com.company.khomasi.presentation.components.cards.BookingStatus
import com.company.khomasi.presentation.components.cards.Playground
import com.company.khomasi.presentation.favorite.MockViewModel
import com.company.khomasi.presentation.favorite.TopBar
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun MyBookingPage(

) {
    val pagerState: PagerState = PagerState { 0;" ";2 }

    HorizontalPager(state = pagerState) { index ->
        when (index) {
            0 -> {
              // PreviousPage(myBookingPlaygrounds = , uiState = , myBooking = )
            }

            1 -> {
                //CurrentPage()
            }

        }
    }
}
@Composable
fun PreviousPage(
    myBookingPlaygrounds: (String) -> Unit,
    uiState: StateFlow<MyBookingUiState>,
    myBooking: StateFlow<DataState<MyBookingsResponse>>
){

//    val viewModel: MyBookingViewModel = viewModel()
//    val uiState: StateFlow<MyBookingUiState> = viewModel.uiState
//    val myBooking: StateFlow<DataState<MyBookingsResponse>> = viewModel.myBooking

    val currentState = uiState.collectAsState().value
    val myBookingState by myBooking.collectAsState()

    DisposableEffect(Unit) {
        myBookingPlaygrounds(currentState.userId)
        onDispose {}
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar() },

        ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp),
            color = MaterialTheme.colorScheme.background,
        ) {
            when (myBookingState) {
                is DataState.Success -> {
                    val response = (myBookingState as DataState.Success).data
                    if (response.results.isNotEmpty()) {
                        LazyColumn(
                            contentPadding = it,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(response.results) {
                                BookingCard(
                                    com.company.khomasi.presentation.components.cards.BookingDetails(
                                        "1/10/2024",
                                        "7 AM to 8 AM",
                                        "50 $ per hour ",
                                        "2425",
                                        Playground(
                                            "Zsc",
                                            "Tanta",
                                            "https://2u.pw/KqnLykO",
                                            3.8f,
                                            "50 $ per hour",
                                            "from 12 PM to 12 AM",
                                            isFavorite = true,
                                            isBookable = false
                                        ),
                                        statusOfBooking = BookingStatus.CONFIRMED
                                    ),
                                )
                            }
                        }
                    } else {
                        com.company.khomasi.presentation.favorite.EmptyScreen()
                    }
                }
                is DataState.Loading -> {
                }
                is DataState.Error -> {
                }
                else -> {
                }
            }
        }
    }
}

@Composable
fun CurrentPage(
    myBookingPlaygrounds: (String) -> Unit,
    uiState: StateFlow<MyBookingUiState>,
    myBooking: StateFlow<DataState<MyBookingsResponse>>
){

//    val viewModel: MyBookingViewModel = viewModel()
//    val uiState: StateFlow<MyBookingUiState> = viewModel.uiState
//    val myBooking: StateFlow<DataState<MyBookingsResponse>> = viewModel.myBooking

    val currentState = uiState.collectAsState().value
    val myBookingState by myBooking.collectAsState()

    DisposableEffect(Unit) {
        myBookingPlaygrounds(currentState.userId)
        onDispose {}
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar() },

        ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp),
            color = MaterialTheme.colorScheme.background,
        ) {
            when (myBookingState) {
                is DataState.Success -> {
                    val response = (myBookingState as DataState.Success).data
                    if (response.results.isNotEmpty()) {
                        LazyColumn(
                            contentPadding = it,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(response.results) {
                                BookingCard(
                                    com.company.khomasi.presentation.components.cards.BookingDetails(
                                        "1/10/2024",
                                        "7 AM to 8 AM",
                                        "50 $ per hour ",
                                        "2425",
                                        Playground(
                                            "Zsc",
                                            "Tanta",
                                            "https://2u.pw/KqnLykO",
                                            3.8f,
                                            "50 $ per hour",
                                            "from 12 PM to 12 AM",
                                            isFavorite = true,
                                            isBookable = false
                                        ),
                                        statusOfBooking = BookingStatus.CONFIRMED
                                    ),
                                )
                            }
                        }
                    } else {
                        com.company.khomasi.presentation.favorite.EmptyScreen()
                    }
                }
                is DataState.Loading -> {
                }
                is DataState.Error -> {
                }
                else -> {
                }
            }
        }
    }
}


@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun tabRow(pagerState: PagerState) {
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        divider = {
            Spacer(modifier = Modifier.height(5.dp))
        },
//        indicator = { tabPositions ->
//            SecondaryIndicator(
//                modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
//                height = 5.dp,
//                color = Color.White
//            )
//        },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaygroundInfo(bookingDetails: BookingDetails) {
    Column {
        TopAppBar(
            title = {
                Text(
                    text = bookingDetails.name,
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.CenterStart)
                        .padding(start = 16.dp)
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.background)
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(), thickness = 1.dp
        )
        BookingCard(
            com.company.khomasi.presentation.components.cards.BookingDetails(
                "1/10/2024",
                "7 AM to 8 AM",
                "50 $ per hour ",
                "2425",
                Playground(
                    "Zsc",
                    "Tanta",
                    "https://2u.pw/KqnLykO",
                    3.8f,
                    "50 $ per hour",
                    "from 12 PM to 12 AM",
                    isFavorite = true,
                    isBookable = false
                ),
                statusOfBooking = BookingStatus.CONFIRMED
            ),
        )

    }

}