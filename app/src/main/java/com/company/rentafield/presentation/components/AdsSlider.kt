package com.company.rentafield.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.company.rentafield.utils.gradientOverlay
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
            val contentText = stringResource(adsContent[page].contentText)
            val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
            val (scale, alpha) = remember(pageOffset) {
                val scale = lerp(0.85f, 1f, 1f - pageOffset.coerceIn(0f, 1f))
                val alpha = lerp(0.5f, 1f, 1f - pageOffset.coerceIn(0f, 1f))
                scale to alpha
            }
            val clickModifier = remember(page) {
                if (contentText == "Upload Video and Win Coins Now!")
                    Modifier.clickable { onAdClicked(userId) }
                else Modifier
            }
            Card(
                shape = MaterialTheme.shapes.large,
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        this.alpha = alpha
                    }
                    .then(clickModifier)
            ) {
                Box(
                    contentAlignment = Alignment.BottomStart,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(adsContent[page].imageSlider),
                        contentDescription = "image slider",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .gradientOverlay(.6f)
                    )
                    Text(
                        text = contentText,
                        color = Color.White,
                        style = MaterialTheme.typography.displayLarge,
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Start,
                        maxLines = 2
                    )
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
