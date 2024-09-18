package com.company.rentafield.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.company.rentafield.R
import com.company.rentafield.presentation.screens.home.model.AdsContent
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AdsSlider(
    adsContent: List<AdsContent>,
    userId: String,
    onAdClicked: (String) -> Unit = {}
) {

    val pagerState = rememberPagerState(initialPage = 0)
    val isDraggedState = pagerState.interactionSource.collectIsDraggedAsState()
    val imageSlider = remember { adsContent.map { it.imageSlider } }
    val contentSlider = remember { adsContent.map { it.contentText } }
    LaunchedEffect(isDraggedState) {
        snapshotFlow { isDraggedState.value }
            .collectLatest { isDragged ->
                if (!isDragged) {
                    while (true) {
                        delay(2600)
                        runCatching {
                            pagerState.animateScrollToPage(pagerState.currentPage.inc() % pagerState.pageCount)
                        }
                    }
                }
            }
    }

    Column {
        HorizontalPager(
            count = adsContent.size,
            state = pagerState,
            modifier = Modifier
                .height(180.dp)
                .fillMaxWidth()
        ) { page ->
            Card(
                shape = MaterialTheme.shapes.large,
                modifier = Modifier
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                        lerp(
                            start = 0.85f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }

                        alpha = lerp(
                            start = 0.5f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )

                    }
                    .then(
                        if (stringResource(adsContent[page].contentText) == "Upload Video and Win Coins Now!")
                            Modifier.clickable { onAdClicked(userId) }
                        else
                            Modifier
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(imageSlider[page]),
                        contentDescription = "image slider",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .drawWithCache {
                                onDrawWithContent {
                                    drawContent()
                                    drawRect(
                                        Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                Color.Black.copy(alpha = .6f)
                                            ),
                                            startY = 0f,
                                            endY = Float.POSITIVE_INFINITY
                                        )
                                    )
                                }
                            }
                    )
                    Column(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = stringResource(contentSlider[page]),
                            color = Color.White,
                            style = MaterialTheme.typography.displayLarge,
                            modifier = Modifier.padding(8.dp),
                            textAlign = TextAlign.Start,
                            maxLines = 2
                        )
                    }
                }
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            activeColor = Color.LightGray,
            inactiveColor = Color.DarkGray,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 8.dp)
        )
    }
}

@Preview(locale = "ar")
@Composable
fun AdsSliderPreview() {
    AdsSlider(
        mutableListOf(
            AdsContent(
                R.drawable.playground,
                R.string.ad_content_2
            )
        ),
        userId = "userId"
    )
}
