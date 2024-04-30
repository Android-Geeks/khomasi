package com.company.khomasi.presentation.playground


//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.khomasi.R
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.PlaygroundReviewResponse
import com.company.khomasi.domain.model.PlaygroundScreenResponse
import com.company.khomasi.presentation.components.AuthSheet
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.MyModalBottomSheet
import com.company.khomasi.presentation.components.connectionStates.ThreeBounce
import com.company.khomasi.presentation.playground.components.ImageSlider
import com.company.khomasi.presentation.playground.components.PlaygroundDefinition
import com.company.khomasi.presentation.playground.components.PlaygroundDescription
import com.company.khomasi.presentation.playground.components.PlaygroundFeatures
import com.company.khomasi.presentation.playground.components.PlaygroundRatesAndReviews
import com.company.khomasi.presentation.playground.components.PlaygroundReviews
import com.company.khomasi.presentation.playground.components.PlaygroundRules
import com.company.khomasi.presentation.playground.components.PlaygroundSize
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.theme.darkIcon
import com.company.khomasi.theme.lightIcon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun PlaygroundScreen(
    playgroundId: Int,
    playgroundStateFlow: StateFlow<DataState<PlaygroundScreenResponse>>,
    playgroundUiState: StateFlow<PlaygroundUiState>,
    reviewsState: StateFlow<DataState<PlaygroundReviewResponse>>,
    context: Context = LocalContext.current,
    onViewRatingClicked: () -> Unit,
    getPlaygroundDetails: (Int) -> Unit,
    onClickBack: () -> Unit,
    onClickShare: () -> Unit,
    onClickFav: () -> Unit,
    onBookNowClicked: () -> Unit,
    onClickDisplayOnMap: () -> Unit,
    updateShowReview: () -> Unit
) {
    var showLoading by remember { mutableStateOf(false) }
//    val uiState by playgroundUiState.collectAsStateWithLifecycle()
    val playgroundState by playgroundStateFlow.collectAsStateWithLifecycle()
    var playgroundData by remember { mutableStateOf<PlaygroundScreenResponse?>(null) }
    val reviews by reviewsState.collectAsStateWithLifecycle()
    val bottomSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        getPlaygroundDetails(playgroundId)
        Log.d("lol", "getPlaygroundDetails called")
    }

    playgroundState.let { state ->
        when (state) {
            is DataState.Loading -> {
                showLoading = true
            }

            is DataState.Success -> {
                showLoading = false
                playgroundData = state.data
            }

            is DataState.Error -> {
                showLoading = false
                // handle error
            }

            is DataState.Empty -> {
                showLoading = false
            }
        }
    }
    AuthSheet(
        sheetModifier = Modifier.fillMaxWidth(),
        screenContent = {
//            Log.d("lol", "AuthSheet recomposed")

            PlaygroundScreenContent(
                playgroundData = playgroundData,
                uiState = playgroundUiState,
                onViewRatingClicked = onViewRatingClicked,
                onClickBack = onClickBack,
                onClickShare = onClickShare,
                onClickFav = { onClickFav() },
                onClickDisplayOnMap = { onClickDisplayOnMap() })
        },
        sheetContent = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .height(116.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = context.getString(
                        R.string.fees_per_hour, playgroundData?.playground?.feesForHour ?: 0
                    )
                )

                MyButton(
                    text = R.string.book_now,
                    onClick = {
                        onBookNowClicked()
                    },
                    modifier = Modifier.fillMaxWidth()
                )

            }
        })

    ShowBottomSheet(
        playgroundUiState = playgroundUiState,
        bottomSheetState = bottomSheetState,
        scope = scope,
        reviews = reviews,
        updateShowReview = updateShowReview
    )


    if (showLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            ThreeBounce(
                color = MaterialTheme.colorScheme.primary,
                size = DpSize(75.dp, 75.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowBottomSheet(
    playgroundUiState: StateFlow<PlaygroundUiState>,
    bottomSheetState: SheetState,
    scope: CoroutineScope,
    reviews: DataState<PlaygroundReviewResponse>,
    updateShowReview: () -> Unit
) {
    val uiState by playgroundUiState.collectAsStateWithLifecycle()
    if (uiState.showReviews) {
        MyModalBottomSheet(
            sheetState = bottomSheetState,
            onDismissRequest = {
                dismissBottomSheet(bottomSheetState, scope, updateShowReview)
            },
            modifier = Modifier,
            content = {
                PlaygroundReviews(
                    reviews = reviews,
                    onClickCancel = {
                        dismissBottomSheet(bottomSheetState, scope, updateShowReview)
                    })
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun dismissBottomSheet(
    bottomSheetState: SheetState,
    scope: CoroutineScope,
    updateShowReview: () -> Unit
) {
    scope.launch {
        bottomSheetState.hide()
        updateShowReview()
    }
}

@Composable
fun PlaygroundScreenContent(
    playgroundData: PlaygroundScreenResponse?,
    uiState: StateFlow<PlaygroundUiState>,
    onViewRatingClicked: () -> Unit,
    onClickBack: () -> Unit,
    onClickShare: () -> Unit,
    onClickFav: () -> Unit,
    onClickDisplayOnMap: () -> Unit
) {
//    Log.d("lol", "PlaygroundScreenContent recomposed")
    LazyColumn(
        Modifier.fillMaxSize()
    ) {

        item {
//            Log.d("lol", "imgSlider recomposed")
            ImageSlider(
                imageList = playgroundData?.playgroundPictures ?: emptyList(),
                isFav = false,
                onClickBack = { onClickBack() },
                onClickShare = { onClickShare() },
                onClickFav = { onClickFav() })
        }

        item {
//            Log.d("lol", "PlaygroundDefinition recomposed")
            PlaygroundDefinition(name = playgroundData?.playground?.name ?: "",
                openingTime = playgroundData?.playground?.openingHours ?: "",
                address = playgroundData?.playground?.address ?: "",
                onClickDisplayOnMap = { onClickDisplayOnMap() })
        }

        item { LineSpacer() }

        item {
            val ui = uiState.collectAsStateWithLifecycle().value.reviewsCount
            PlaygroundRatesAndReviews(
                rateNum = ui.toString(),
                rate = playgroundData?.playground?.rating.toString(),
                onViewRatingClicked = onViewRatingClicked
            )
        }

        item { LineSpacer() }

        item { PlaygroundSize(size = stringResource(R.string.field_size_5x5)) }

        item { LineSpacer() }

        item { PlaygroundDescription(description = playgroundData?.playground?.description ?: "") }

        item { LineSpacer() }

        item {
            playgroundData?.playground?.advantages.let {
                if (it != null) {
                    PlaygroundFeatures(
                        featureList = it.split(
                            ","
                        )
                    )
                }
            }
        }

        item { LineSpacer() }


        item {
            PlaygroundRules(rulesList = "playgroundData.playground.rules".split(","))
        }

        item { Spacer(modifier = Modifier.height(146.dp)) }
    }
}


@Composable
fun ButtonWithIcon(
    iconId: Int, onClick: () -> Unit
) {
    val currentLanguage = Locale.getDefault().language
    Card(shape = CircleShape, modifier = Modifier
        .size(44.dp)
        .clickable { onClick() }) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null,
                tint = if (isSystemInDarkTheme()) darkIcon else lightIcon,
                modifier = Modifier
                    .size(24.dp)
                    .then(
                        if (currentLanguage == "en") {
                            Modifier.rotate(180f)
                        } else {
                            Modifier
                        }
                    )
            )
        }
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
            painter = painterResource(iconId), contentDescription = null,
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
fun LineSpacer() {
    Spacer(
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
    val mockViewModel: MockPlaygroundViewModel = hiltViewModel()
    KhomasiTheme {
        PlaygroundScreen(
            playgroundId = 1,
            playgroundStateFlow = mockViewModel.playgroundState,
            playgroundUiState = mockViewModel.uiState,
            reviewsState = mockViewModel.reviewsState,
            onViewRatingClicked = {},
            onClickShare = {},
            onClickBack = {},
            onClickFav = {},
            onBookNowClicked = { mockViewModel.onBookNowClicked() },
            onClickDisplayOnMap = {},
            getPlaygroundDetails = { mockViewModel.getPlaygroundDetails(1) },
            updateShowReview = {}
        )
    }

}
