package com.company.khomasi.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.fontscaling.MathUtils.lerp
import androidx.compose.ui.util.lerp
import com.company.khomasi.theme.KhomasiTheme
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(){

    /*Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "Home Screen"
        )
    }*/

    Column{
//        val pagerState = rememberPagerState(pageCount = { 4 })
//        HorizontalPager(
//            state = pagerState,
//            modifier = Modifier.fillMaxSize()
//        ) { page ->
//            Log.d("HomeScreen", "page: $page")
//            // Our page content
//            Card{
//                Text(
//                    text = "Page: $page",
//                )
//            }
//        }

        val itemList = (0..10).toList()
        val itemsPerPage = 3 // Number of items per page
        val totalPages = (itemList.size + itemsPerPage - 1) / itemsPerPage

        LazyRow {
            items(totalPages) { pageIndex ->
                Row {
                    val startIndex = pageIndex * itemsPerPage
                    val endIndex = minOf((pageIndex + 1) * itemsPerPage, itemList.size)

                    for (i in startIndex until endIndex) {
                        Box(
                            modifier = Modifier
                                .width(200.dp) // Adjust width as needed
                                .padding(8.dp)
                        ) {
                            // Content of each item
                        }
                    }
                }
            }
        }

    }
}


@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview(){
    KhomasiTheme {
        HomeScreen()
    }
}


/*
repeat(pagerState.pageCount) { iteration ->
    Log.d("HomeScreen", "HomeScreen: $iteration")
    Log.d("HomeScreen", "currentScreen: ${pagerState.currentPage}")
    val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
    Box(
        modifier = Modifier
            .padding(2.dp)
            .clip(CircleShape)
            .background(color)
            .size(16.dp)
    )
}*/


/*
    val threePagesPerViewport = object : PageSize {
        override fun Density.calculateMainAxisPageSize(
            availableSpace: Int,
            pageSpacing: Int
        ): Int {
            Log.d("HomeScreen", "availableSpace: $availableSpace")
            Log.d("HomeScreen", "pageSpacing: $pageSpacing")
            Log.d("HomeScreen", "${(availableSpace - 2 * pageSpacing) / 3}")
            return (availableSpace - 2 * pageSpacing) / 3
        }
    }*/

/*
.graphicsLayer {
                    // Calculate the absolute offset for the current page from the
                    // scroll position. We use the absolute value which allows us to mirror
                    // any effects for both directions
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue

                    // We animate the alpha, between 50% and 100%
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
* */