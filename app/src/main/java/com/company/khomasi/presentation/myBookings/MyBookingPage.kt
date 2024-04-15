package com.company.khomasi.presentation.myBookings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.LeadingIconTab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.company.khomasi.presentation.myBookings.components.TabItem
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
@OptIn(ExperimentalPagerApi::class)
@Composable
fun MyBookingPage(
) {
    val list = listOf(TabItem.Current, TabItem.Expired)
    val pagerState = rememberPagerState(initialPage = 0)
    Column(modifier = Modifier.fillMaxSize()) {
        Tabs(tabs = list, pagerState = pagerState)
        TabContent(
            tabs = list,
            pagerState = pagerState,
        )
    }
}
@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(tabs: List<TabItem>, pagerState: PagerState) {

    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.background,
        indicator = { tabPositions ->
            Modifier.pagerTabIndicatorOffset(pagerState = pagerState, tabPositions = tabPositions)
        }) {
        tabs.forEachIndexed { index, tabItem ->

            LeadingIconTab(
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = { Text(tabItem.title) },
                selectedContentColor = Color.Green,
                unselectedContentColor = Color.White,
                icon = { },
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
) {
    HorizontalPager(count = tabs.size, state = pagerState) { page ->
        tabs[page].screens()

    }
}


//import androidx.compose.foundation.ExperimentalFoundationApi
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.pager.HorizontalPager
//import androidx.compose.foundation.pager.PagerState
//import androidx.compose.material.TabRow
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Tab
//import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import com.company.khomasi.domain.model.PlaygroundPicture
//import com.company.khomasi.presentation.myBookings.components.CurrentPage
//import com.company.khomasi.presentation.myBookings.components.ExpiredPage
//import com.google.accompanist.pager.ExperimentalPagerApi
//import com.google.accompanist.pager.pagerTabIndicatorOffset
//import com.google.accompanist.pager.rememberPagerState
//import kotlinx.coroutines.flow.StateFlow
//
//@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
//@Composable
//fun MyBookingPage(
//    uiState: StateFlow<MyBookingUiState>,
//    playgroundPicture: PlaygroundPicture,
//   // pagerState: PagerState, // Change the type here
//) {
//    var currentPage by remember { mutableStateOf(0) }
//    var pagerState = rememberPagerState(initialPage = currentPage)
//
//    Column {
//        TabRow(
//            selectedTabIndex = currentPage,
//            backgroundColor = MaterialTheme.colorScheme.surface,
//            contentColor = MaterialTheme.colorScheme.onSurface,
//            indicator = { tabPositions ->
//                SecondaryIndicator(
//                    modifier = Modifier
//                        //.pagerTabIndicatorOffset(pagerState, tabPositions)
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
//        HorizontalPager(
//            state = pagerState,
//            modifier = Modifier.fillMaxWidth()
//        ) { page ->
//            when (page) {
//                0 -> {
//                    CurrentPage(
//                        uiState = uiState,
//                        playgroundPicture = playgroundPicture
//                    )
//                }
//
//                1 -> {
//                    ExpiredPage(uiState, playgroundPicture = playgroundPicture)
//                }
//            }
//        }
//    }
//}