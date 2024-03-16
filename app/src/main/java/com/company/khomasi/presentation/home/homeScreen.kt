package com.company.khomasi.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.company.khomasi.R
import com.company.khomasi.domain.DataState
import com.company.khomasi.presentation.components.cards.Playground
import com.company.khomasi.presentation.components.cards.PlaygroundCard
import com.company.khomasi.theme.KhomasiTheme

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    onSearchBarClicked: () -> Unit = {}
) {
    val homeState = homeViewModel.homeState.collectAsState().value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 12.dp)
    ) {

        Row {
/*            AsyncImage(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .padding(end = 4.dp),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(homeState.userImg?.convertToBitmap()).crossfade(true).build(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                placeholder = painterResource(id = R.drawable.user_img)
            )*/
            Image(
                painter = painterResource(id = R.drawable.user_img),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .padding(end = 4.dp),
            )
            Column {
                Text(
                    text = "${stringResource(id = R.string.hello)} ${stringResource(id = R.string.user_name)}",
                    style = MaterialTheme.typography.bodyMedium,

                    )

                Text(
                    text = stringResource(id = R.string.welcome_message),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.bell), contentDescription = null
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Card(colors = CardDefaults.cardColors(Color.Transparent),
            modifier = Modifier
                .clickable { onSearchBarClicked() }
                .fillMaxWidth()
                .height(38.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = MaterialTheme.shapes.medium
                )) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.search_for_playgrounds),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.tertiary
                )

                Spacer(modifier = Modifier.weight(1f))

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.magnifyingglass),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.size(19.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {       //////// Ads SLIDER here
                AdsItem()
            }
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(id = R.string.nearby_fields),
                        style = MaterialTheme.typography.displayMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(id = R.string.view_all),
                        style = MaterialTheme.typography.displayMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            if (homeState is DataState.Success) {
                items(homeState.data.playgrounds.sortedBy { it.id }) {
                    PlaygroundCard(
                        playground = Playground(
                            name = it.name,
                            address = it.address,
                            imageUrl = it.playgroundPicture, /* -------------NULL------------*/
                            rating = it.rating.toFloat(),
                            price = it.feesForHour.toString(),
                            openingHours = "",
                            isFavorite = false, /* -------------NOT FOUND------------*/
                            isBookable = it.isBookable
                        )
                    )
                }
            }

        }
    }

}


@Composable
fun AdsItem(
    image: Painter = painterResource(id = R.drawable.playground),
    content: String = " احجز اى ملعب بخصم 10 %",
    adsNum: Int = 3
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        colors = CardDefaults.cardColors(Color.Transparent),
        shape = MaterialTheme.shapes.medium
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            Column {
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = content,
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.White,
                    modifier = Modifier.padding(8.dp)
                )
                Row{
                    repeat(3) { iteration ->

//                    val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray)
                                .size(16.dp)
                        )
                    }
                }

            }
        }
    }
}

@Preview(showSystemUi = true, locale = "ar")
@Composable
fun HomeScreenPreview() {
    KhomasiTheme {
        HomeScreen(hiltViewModel()) {}

    }

}
