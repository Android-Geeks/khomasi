package com.company.khomasi.presentation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.company.khomasi.R
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.LocalUser
import com.company.khomasi.domain.model.PlaygroundsResponse
import com.company.khomasi.presentation.components.AdsContent
import com.company.khomasi.presentation.components.AdsSlider
import com.company.khomasi.presentation.components.cards.Playground
import com.company.khomasi.presentation.components.cards.PlaygroundCard
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.utils.convertToBitmap

@SuppressLint("ResourceType")
@Composable
fun HomeScreen(
    playgroundState: DataState<PlaygroundsResponse>,
    homeUiState: HomeUiState ,
    userData: LocalUser,
    onClickBell: () -> Unit = {},
    onSearchBarClicked: () -> Unit = {},
    onClickViewAll : () -> Unit = {}
) {
    val profileImg = userData.profilePicture

    //        -----------------Temporary-----------------           //
    val adsList = listOf(
        AdsContent(
            imageSlider = painterResource(id = R.drawable.playground),
            contentText = " احجز اى ملعب بخصم 10 %",
        ),
        AdsContent(
            imageSlider = painterResource(id = R.drawable.playground_image),
            contentText = "احصل على خصم 20% عند دعوة 5 أصدقاء",
        ),
        AdsContent(
            imageSlider = painterResource(id = R.drawable.playground),
            contentText = " احجز اى ملعب صباحًا بخصم 30 %",
        ),
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 12.dp)
    ) {
        Row {
            AsyncImage(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .padding(end = 4.dp),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(if (profileImg.isNullOrEmpty() ){
                        stringResource(id = R.drawable.userpic1)
                    } else{profileImg.convertToBitmap()})
                    .crossfade(true).build(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                placeholder = painterResource(id = R.drawable.userpic1)
            )

            Column {
                Text(
                    text = "${stringResource(id = R.string.hello)} ${userData.firstName}",
                    style = MaterialTheme.typography.bodyMedium,

                    )

                Text(
                    text = stringResource(id = R.string.welcome_message),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = { onClickBell()}) {
                Icon(
                    painter = painterResource(id = R.drawable.bell), contentDescription = null
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            colors = CardDefaults.cardColors(Color.Transparent),
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

            item { AdsSlider(adsContent = adsList) }

            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(id = R.string.nearby_fields),
                        style = MaterialTheme.typography.displayMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(id = R.string.view_all),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable { onClickViewAll() }
                    )
                }
            }

            if (playgroundState is DataState.Success) {
                items(playgroundState.data.playgrounds.sortedBy { it.id }.take(homeUiState.playgroundCount)) {
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

@Preview(showSystemUi = true, locale = "ar")
@Composable
fun HomeScreenPreview() {
    KhomasiTheme {
        val mockViewMode: MockHomeViewModel = hiltViewModel()
        HomeScreen(
            playgroundState = mockViewMode.playgroundState.collectAsState().value,
            homeUiState = mockViewMode.homeUiState.collectAsState().value,
            userData = mockViewMode.userData
        )

    }

}
