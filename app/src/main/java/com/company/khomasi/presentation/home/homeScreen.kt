package com.company.khomasi.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.theme.KhomasiTheme

@Composable
fun HomeScreen(
    homeUiState: HomeUiState,
    onSearchBarClicked: () -> Unit
) {
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
                    .data(homeUiState.userImg?.convertToBitmap()).crossfade(true).build(),
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
                    text = stringResource(id = R.string.hello) + " " + stringResource(id = R.string.user_name),
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
                    painter = painterResource(id = R.drawable.bell),
                    contentDescription = null
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
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
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
    }

}

@Preview(showSystemUi = true, locale = "ar")
@Composable
fun HomeScreenPreview() {
    KhomasiTheme {
        HomeScreen(
            homeUiState = HomeUiState(
                "", ""
            )
        ) {}
    }

}