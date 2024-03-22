package com.company.khomasi.presentation.playground


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.company.khomasi.domain.model.PlaygroundScreenResponse
import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.company.khomasi.R
import com.company.khomasi.domain.DataState
import com.company.khomasi.presentation.components.AuthSheet
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.MyOutlinedButton
import com.company.khomasi.theme.KhomasiTheme


@Composable
fun PlaygroundScreen(
    playgroundState: DataState<PlaygroundScreenResponse>,
    context: Context = LocalContext.current,
    onViewRatingClicked: () -> Unit
) {

    if (playgroundState is DataState.Success) {

        val playgroundData = playgroundState.data
        AuthSheet(
            sheetModifier = Modifier.fillMaxWidth(),
            screenContent = {
//                PlaygroundScreenContent(playgroundData)
                PlaygroundScreenContent(onViewRatingClicked = onViewRatingClicked)
            },
        ) {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Text(
                    text = context.getString(
                        R.string.fees_per_hour, playgroundData.playground.feesForHour
                    )
                )

                MyButton(
                    text = R.string.book, onClick = { }, modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }

}

@Composable
fun PlaygroundScreenContent(
//    playgroundData: PlaygroundScreenResponse,
    onViewRatingClicked: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
//        ---------playground img----------


        PlaygroundDefinition(
            name = "ملعب الشهداء",
            openingTime = "5:00 ص - 8:00م",
            address = "شارع النيل حى الزمالك",
        )

        LineDivider()

        PlaygroundRates(
            rateNum = "23", rate = "4.8", onViewRatingClicked = onViewRatingClicked
        )

        LineDivider()

        PlaygroundSize(size = "5 x 5")

        LineDivider()

        PlaygroundDescription(description = "ملعب كرة قدم يتميز بعشب أخضر نضير، ومدرجات حماسية، يُعتبر مكانًا مثاليًا للتنافس الرياضي وجمع الجماهير.")

        LineDivider()

        PlaygroundFeatures(featureList = ("دوره مياه , إضاءه , موقف , واى فاى").split(","))

        LineDivider()

        PlaygroundRules(rulesList = ("ارتداء الكمامة ,تعقيم الأيدي").split(","))

    }
}

@Composable
fun PlaygroundFeatures(
    featureList: List<String>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.field_features),
            style = MaterialTheme.typography.titleLarge
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 100.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(featureList) { feature ->
                Card(
                    modifier = Modifier
                        .height(50.dp)
                        .width(IntrinsicSize.Min), // Adjust width based on content
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(Color.Transparent)
                ) {
                    Text(
                        text = feature,
                        style = MaterialTheme.typography.displayMedium,
                        color = MaterialTheme.colorScheme.tertiary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 3.dp, horizontal = 10.dp)
                    )
                }
            }
        }

    }
}

@Composable
fun PlaygroundDefinition(
    name: String,
    openingTime: String,
    address: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {

        Text(
            text = name, style = MaterialTheme.typography.titleLarge
        )


        IconWithText(text = openingTime, iconId = R.drawable.clock)

        IconWithText(text = address, iconId = R.drawable.mappin)

    }
}

@Composable
fun PlaygroundSize(
    size: String
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(
            text = stringResource(id = R.string.field_size),
            style = MaterialTheme.typography.titleLarge
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = MaterialTheme.shapes.medium
                ), colors = CardDefaults.cardColors(Color.Transparent)
        ) {
            Row(
                Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = size,
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }

        }
    }
}

@Composable
fun PlaygroundRules(
    rulesList: List<String>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.field_instructions),
            style = MaterialTheme.typography.titleLarge,
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            itemsIndexed(rulesList) { index, item ->
                Text(
                    text = " ${index + 1}. $item",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}

@Composable
fun PlaygroundRates(
    rateNum: String, rate: String, onViewRatingClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row {
            Text(
                text = stringResource(id = R.string.ratings),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "($rateNum ${stringResource(id = R.string.rate)})",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    text = rate, style = MaterialTheme.typography.bodyLarge
                )
                Icon(
                    painter = painterResource(R.drawable.unfilled_star),
                    contentDescription = null
                )
            }
        }

        MyOutlinedButton(
            text = R.string.view_ratings,
            onClick = { onViewRatingClicked() },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun PlaygroundDescription(
    description: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.field_description),
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = description,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Composable
fun IconWithText(
    @DrawableRes iconId: Int,
    text: String,
) {
    Row(
        Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(iconId), contentDescription = null
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Composable
fun LineDivider() {

    Divider(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .height(0.5.dp)
            .border(width = 0.5.dp, color = MaterialTheme.colorScheme.outline)
    )

}

@Preview(locale = "ar", showSystemUi = true)
@Composable
fun PlaygroundScreenPreview() {
//    val mockViewModel: MockPlaygroundViewModel = hiltViewModel()

    KhomasiTheme {
        PlaygroundScreenContent {}
    }
}


/*

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun PlaygroundScreen() {
    val playgroundViewModel: PlaygroundViewModel = hiltViewModel()
    val state = playgroundViewModel.playgroundState.collectAsState().value
    LaunchedEffect(key1 = playgroundViewModel.playgroundState.value) {
        Log.d("FavoriteScreen", "FavoriteScreen: ${playgroundViewModel.playgroundState.value}")
    }
    if (state is DataState.Success) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Text(text = state.data.playground.holidays)
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(state.data.playgroundPictures[0].picture.convertToBitmap())
                    .crossfade(true).build(), contentDescription = null
            )
        }
    }

}

@Preview
@Composable
fun kkk() {
    KhomasiTheme {
        PlaygroundScreen()
    }
}

/*KhomasiTheme {
        PlaygroundScreen(
            playground = PlaygroundScreenResponse(
                playground = PlaygroundX(
                    20,
                    "string",
                    "string",
                    "Wide,Open",
                    "string",
                    5,
                    5.0,
                    "string",
                    "string",
                    0.34122,
                    0.62543,
                    "Friday,Saturday",
                    "1:24",
                    50,
                    50,
                    true,
                    "No Rules"
                ),
                busyTimes = listOf(BusyTime("string", 5)),

                playgroundPictures = listOf(
                    PlaygroundPicture(
                        1,
                        1,
                        "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",
                        false
                    )
                )
            )
        )
    }*/
*/
