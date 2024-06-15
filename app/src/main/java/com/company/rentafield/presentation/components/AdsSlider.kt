package com.company.rentafield.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.company.rentafield.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue

data class AdsContent(
    val imageSlider: Painter,
    val contentText: String = "",
)

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AdsSlider(
    adsContent: List<AdsContent>,
    onAdClicked: () -> Unit = {}
) {

    val pagerState = rememberPagerState(initialPage = 0)
    val imageSlider = remember { adsContent.map { it.imageSlider } }
    val contentSlider = remember { adsContent.map { it.contentText } }
    LaunchedEffect(Unit) {
        while (true) {
            yield()
            delay(2600)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount)
            )
        }
    }

    Column {
        HorizontalPager(
            count = adsContent.size,
            state = pagerState,
            modifier = Modifier
                .height(180.dp)
                .fillMaxWidth()
                .clickable { onAdClicked() }
        ) { page ->
            Card(shape = MaterialTheme.shapes.large, modifier = Modifier.graphicsLayer {
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
            }) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = imageSlider[page],
                        contentDescription = "image slider",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Column(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = contentSlider[page],
                            color = Color.White,
                            fontSize = 18.sp,
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
        listOf(AdsContent(painterResource(id = R.drawable.playground), " احجز اى ملعب بخصم 10 %")),
    )
}
