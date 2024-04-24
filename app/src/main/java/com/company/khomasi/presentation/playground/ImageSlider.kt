package com.company.khomasi.presentation.playground

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.company.khomasi.R
import com.company.khomasi.domain.model.PlaygroundPicture
import com.company.khomasi.presentation.components.iconButtons.FavoriteIcon
import com.company.khomasi.utils.convertToBitmap
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageSlider(
    imageList: List<PlaygroundPicture>,
    isFav: Boolean,
    onClickBack: () -> Unit,
    onClickShare: () -> Unit,
    onClickFav: () -> Unit
) {
    val pagerState = rememberPagerState(initialPage = 0)
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(246.dp)
    ) {
        Log.d("ImageSlider", "Re: ${imageList.size}")
        HorizontalPager(
            count = imageList.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(226.dp)
        ) { page ->
            AsyncImage(
                model = ImageRequest.Builder(context = context)
                    .data(imageList[page].picture.convertToBitmap()).crossfade(true).build(),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize(),
//                error = painterResource(id = R.drawable.)
            )

            Column(
                Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom
            ) {
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    inactiveColor = Color.White.copy(alpha = 0.3f),
                    activeColor = Color.White,
                    indicatorWidth = 15.dp,
                    indicatorHeight = 15.dp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 8.dp)
                )

            }
        }
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, start = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ButtonWithIcon(R.drawable.back) { onClickBack() }
            }

            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                ButtonWithIcon(iconId = R.drawable.sharenetwork) { onClickShare() }
                Spacer(modifier = Modifier.width(8.dp))
                Card(
                    shape = CircleShape, modifier = Modifier.size(44.dp)
                ) {
                    Column(
                        Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        FavoriteIcon(
                            onFavoriteClick = { onClickFav() },
                            isFavorite = isFav,
                            modifier = Modifier.size(24.dp),
                            inactiveColor = Color.Black
                        )
                    }
                }
            }
        }
    }
}