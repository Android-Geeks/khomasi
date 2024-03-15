package com.company.khomasi.presentation.favorite

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.theme.KhomasiTheme

@Composable
fun FavoritePage() {

    Scaffold(
        topBar = { TopBar() },

        ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            PlaygroundCard(
                it, playground = FavUiState(
                    name = "playground",
                    address = "location",
                    imageUrl = "https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg",
                    rating = 4.9f,
                    price = "price",
                    openingHours = "hour",
                    isFavorite = false,
                    isBookable = false
                )
            )
        }

    }
}
@Composable
fun LazyItem(
    //cards : List<>
){
    LazyColumn {
        item {  }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(modifier: Modifier = Modifier) {

    TopAppBar(
        modifier = modifier.padding(start = 16.dp, bottom = 16.dp),
        title = {
            Row(modifier = modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.my_favorite_stadiums),
                    style = MaterialTheme.typography.displayMedium,
                    //textAlign = TextAlign.Center
                )
            }
        },
        //title = {},
//        actions = {
//         Row (modifier = modifier.fillMaxWidth()){
//             Text(
//                 text = stringResource(R.string.my_favorite_stadiums),
//                 style = MaterialTheme.typography.displayMedium,
//                 textAlign = TextAlign.Start
//             )
//         }
//        },
    )


}

@Preview(showSystemUi = true)
@Composable
fun FavoritePagePreview() {

    KhomasiTheme {
        FavoritePage()
    }
}