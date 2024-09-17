package com.company.rentafield.presentation.screens.playground


import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.rentafield.R
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.playground.PlaygroundReviewsResponse
import com.company.rentafield.domain.model.playground.PlaygroundScreenResponse
import com.company.rentafield.presentation.components.AuthSheet
import com.company.rentafield.presentation.components.MyButton
import com.company.rentafield.presentation.components.MyModalBottomSheet
import com.company.rentafield.presentation.components.connectionStates.ThreeBounce
import com.company.rentafield.presentation.screens.playground.components.ImageSlider
import com.company.rentafield.presentation.screens.playground.components.LineSpacer
import com.company.rentafield.presentation.screens.playground.components.PlaygroundDefinition
import com.company.rentafield.presentation.screens.playground.components.PlaygroundDescription
import com.company.rentafield.presentation.screens.playground.components.PlaygroundFeatures
import com.company.rentafield.presentation.screens.playground.components.PlaygroundRatesAndReviews
import com.company.rentafield.presentation.screens.playground.components.PlaygroundReviews
import com.company.rentafield.presentation.screens.playground.components.PlaygroundRules
import com.company.rentafield.presentation.screens.playground.components.PlaygroundSize
import com.company.rentafield.presentation.screens.playground.model.PlaygroundInfoUiState
import com.company.rentafield.presentation.screens.playground.model.PlaygroundReviewsUiState
import com.company.rentafield.theme.RentafieldTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaygroundScreen(
    playgroundId: Int,
    isFavourite: Boolean,
    playgroundStateFlow: StateFlow<DataState<PlaygroundScreenResponse>>,
    playgroundInfoUiState: StateFlow<PlaygroundInfoUiState>,
    playgroundReviewsUiState: StateFlow<PlaygroundReviewsUiState>,
    reviewsState: StateFlow<DataState<PlaygroundReviewsResponse>>,
    context: Context = LocalContext.current,
    onViewRatingClicked: () -> Unit,
    getPlaygroundDetails: (Int) -> Unit,
    onClickBack: () -> Unit,
    onClickShare: () -> Unit,
    onClickFav: (Boolean) -> Unit,
    onBookNowClicked: () -> Unit,
    updateShowReview: () -> Unit,
    updateFavouriteAndPlaygroundId: (Boolean, Int) -> Unit,
) {
    val showLoading by remember { mutableStateOf(false) }
    val reviews by reviewsState.collectAsStateWithLifecycle()
    val bottomSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        updateFavouriteAndPlaygroundId(isFavourite, playgroundId)
        getPlaygroundDetails(playgroundId)
    }

    AuthSheet(
        sheetModifier = Modifier.fillMaxWidth(),
        screenContent = {
            PlaygroundScreenContent(
                playgroundStateFlow = playgroundStateFlow,
                uiState = playgroundInfoUiState,
                reviewsUiState = playgroundReviewsUiState,
                onViewRatingClicked = onViewRatingClicked,
                onClickBack = onClickBack,
                onClickShare = onClickShare,
                onClickFav = onClickFav,
            )
        },
        sheetContent = {
            val playgroundData = playgroundStateFlow.collectAsStateWithLifecycle().value
            Column(
                Modifier
                    .fillMaxWidth()
                    .height(116.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = context.getString(
                        R.string.fees_per_hour,
                        if (playgroundData is DataState.Success) playgroundData.data.playground.feesForHour else {
                            0
                        }
                    )
                )

                MyButton(
                    text = R.string.book_now,
                    onClick = onBookNowClicked,
                    modifier = Modifier.fillMaxWidth()
                )

            }
        })

    ShowBottomSheet(
        playgroundReviewsUiState = playgroundReviewsUiState,
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
    playgroundReviewsUiState: StateFlow<PlaygroundReviewsUiState>,
    bottomSheetState: SheetState,
    scope: CoroutineScope,
    reviews: DataState<PlaygroundReviewsResponse>,
    updateShowReview: () -> Unit
) {
    val reviewsState by playgroundReviewsUiState.collectAsStateWithLifecycle()
    if (reviewsState.showReviews) {
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
                    }
                )
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
    playgroundStateFlow: StateFlow<DataState<PlaygroundScreenResponse>>,
    uiState: StateFlow<PlaygroundInfoUiState>,
    reviewsUiState: StateFlow<PlaygroundReviewsUiState>,
    onViewRatingClicked: () -> Unit,
    onClickBack: () -> Unit,
    onClickShare: () -> Unit,
    onClickFav: (Boolean) -> Unit,
) {
    LazyColumn(
        Modifier.fillMaxSize()
    ) {
        item {
            ImageSlider(
                playgroundStateFlow = playgroundStateFlow,
                playgroundInfoUiState = uiState,
                onClickBack = onClickBack,
                onClickShare = onClickShare,
                onClickFav = onClickFav
            )
        }

        item {
            PlaygroundDefinition(
                playgroundStateFlow = playgroundStateFlow,
            )
        }

        item { LineSpacer() }

        item {
            PlaygroundRatesAndReviews(
                reviewsUiState = reviewsUiState,
                playgroundStateFlow = playgroundStateFlow,
                onViewRatingClicked = onViewRatingClicked
            )
        }

        item { LineSpacer() }

        item { PlaygroundSize(playgroundStateFlow = playgroundStateFlow) }

        item { LineSpacer() }

        item {
            PlaygroundDescription(
                playgroundStateFlow = playgroundStateFlow,
            )
        }

        item { LineSpacer() }

        item {
            PlaygroundFeatures(
                playgroundStateFlow = playgroundStateFlow,
            )
        }

        item { LineSpacer() }

        item {
            PlaygroundRules(playgroundStateFlow = playgroundStateFlow)
        }

        item { Spacer(modifier = Modifier.height(146.dp)) }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PlaygroundScreenPreview() {
    val mockViewModel: MockPlaygroundViewModel = hiltViewModel()
    RentafieldTheme(darkTheme = false) {
        PlaygroundScreen(
            playgroundId = 1,
            isFavourite = true,
            playgroundStateFlow = mockViewModel.playgroundState,
            playgroundInfoUiState = mockViewModel.uiState,
            reviewsState = mockViewModel.reviewsState,
            onViewRatingClicked = {},
            getPlaygroundDetails = { _ -> },
            onClickBack = {},
            onClickShare = {},
            onClickFav = { _ -> },
            onBookNowClicked = { },
            updateShowReview = {},
            updateFavouriteAndPlaygroundId = { _, _ -> },
            playgroundReviewsUiState = mockViewModel.reviewsUiState
        )
    }

}
