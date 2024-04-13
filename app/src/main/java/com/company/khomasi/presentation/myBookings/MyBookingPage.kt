package com.company.khomasi.presentation.myBookings

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material.TabRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.company.khomasi.domain.model.PlaygroundPicture
import com.company.khomasi.presentation.myBookings.components.CurrentPage
import com.company.khomasi.presentation.myBookings.components.ExpiredPage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.StateFlow

//@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
//@Composable
//fun MyBookingsScreen(
//    uiState: StateFlow<MyBookingUiState>,
//    playgroundPicture: PlaygroundPicture,
//    pagerState: PagerState
//) {
//    var currentPage by remember { mutableStateOf(0) }
//
//    Column {
//        TabRow(
//            selectedTabIndex = currentPage,
//            backgroundColor = MaterialTheme.colorScheme.surface,
//            contentColor = MaterialTheme.colorScheme.onSurface,
//            indicator = { tabPositions ->
//                SecondaryIndicator(
//                    modifier = Modifier
//                        .pagerTabIndicatorOffset(pagerState, tabPositions)
//                        .fillMaxWidth(),
//                    color = MaterialTheme.colorScheme.primary
//                )
//            }
//        ) {
//            Tab(
//                text = { Text("Current") },
//                selected = currentPage == 0,
//                onClick = {
//                    currentPage = 0
//                }
//            )
//            Tab(
//                text = { Text("Expired") },
//                selected = currentPage == 1,
//                onClick = {
//                    currentPage = 1
//                }
//            )
//        }
//
//        MyBookingPage(
//            uiState = uiState,
//            pagerState = pagerState,
//            playgroundPicture = playgroundPicture
//        )
//    }
//}
//
//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun MyBookingPage(
//    uiState: StateFlow<MyBookingUiState>,
//    pagerState: PagerState,
//    playgroundPicture: PlaygroundPicture,
//) {
//    HorizontalPager(
//        state = pagerState,
//        modifier = Modifier.fillMaxWidth()
//    ) { page ->
//        when (page) {
//            0 -> {
//                CurrentPage(
//                    uiState = uiState,
//                    playgroundPicture = playgroundPicture
//                )
//            }
//
//            1 -> {
//                ExpiredPage(uiState, playgroundPicture = playgroundPicture)
//            }
//        }
//    }
//}

@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun MyBookingsScreen(
    uiState: StateFlow<MyBookingUiState>,
    playgroundPicture: PlaygroundPicture,
    pagerState: com.google.accompanist.pager.PagerState,

    ) {
    var currentPage by remember { mutableStateOf(0) }
    val pagerState = rememberPagerState()

    Column {
        TabRow(
            selectedTabIndex = currentPage,
            backgroundColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            indicator = { tabPositions ->
                SecondaryIndicator(
                    modifier = Modifier
                        .pagerTabIndicatorOffset(pagerState, tabPositions)
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        ) {
            Tab(
                text = { Text("Current") },
                selected = currentPage == 0,
                onClick = {
                    currentPage = 0
                }
            )
            Tab(
                text = { Text("Expired") },
                selected = currentPage == 1,
                onClick = {
                    currentPage = 1
                }
            )
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            when (page) {
                0 -> {
                    CurrentPage(
                        uiState = uiState,
                        playgroundPicture = playgroundPicture
                    )
                }

                1 -> {
                    ExpiredPage(uiState, playgroundPicture = playgroundPicture)
                }
            }
        }
    }
}


//@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
//@Composable
//fun MyTabRow(pagerState: PagerState) {
//    val pagerState = rememberPagerState()
//
//    TabRow(
//        selectedTabIndex = pagerState.currentPage,
//        divider = { Spacer(modifier = Modifier.height(1.dp)) },
//        indicator = {
//            SecondaryIndicator(
//                 modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
//                height = 5.dp,
//                color = MaterialTheme.colorScheme.primary
//            )
//        },
//        modifier = Modifier
//            .fillMaxWidth()
//            .wrapContentHeight()
//    ) {
//
//    }
//
//
//}
//@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
//@Composable
//fun MyBookingPage(
//    uiState: StateFlow<MyBookingUiState>,
//    playgroundPicture: PlaygroundPicture,
//) {
//    // val playgroundPic by playgroundPicture.collectAsState()
//
//    val pagerState = PagerState { 0;" ";2 }
//
//
//    HorizontalPager(state = pagerState) { index ->
//        when (index) {
//            0 -> {
//                ExpiredPage(
//                    uiState,
//                    playgroundPicture = playgroundPicture
//                )
//            }
//
//            1 -> {
//                CurrentPage(uiState, playgroundPicture = playgroundPicture)
//            }
//
//        }
//    }
//}