package com.company.khomasi.presentation.myBookings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.domain.DataState
import com.company.khomasi.domain.model.PlaygroundReviewResponse
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.MyTextField
import com.company.khomasi.presentation.components.cards.BookingCard
import com.company.khomasi.presentation.components.cards.BookingStatus
import com.company.khomasi.presentation.components.cards.RatingRow
import com.company.khomasi.presentation.myBookings.MyBookingUiState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun ExpiredPage(
    uiState: StateFlow<MyBookingUiState>,
    playgroundReview: () -> Unit,
    onClickPlayground: (Int) -> Unit,
    responseState: StateFlow<DataState<PlaygroundReviewResponse>>,
    onCommentChange: (String) -> Unit,
    onRatingChange: (Float) -> Unit,
) {
    val expiredState = uiState.collectAsState().value
    val reviewResponse = responseState.collectAsState().value
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberBottomSheetState(BottomSheetValue.Expanded)


    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.rating),
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier.padding(top = 12.dp)
                )
                RatingRow(
                    rating = expiredState.rating.toFloat(),
                    onRatingChange = onRatingChange,
                )
                MyTextField(
                    value = expiredState.comment,
                    onValueChange = onCommentChange,
                    label = R.string.your_comment,
                    keyBoardType = KeyboardType.Text,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {

                    MyButton(
                        onClick = {
                            scope.launch {
                                bottomSheetState.collapse()
                            }
                            playgroundReview()
                            onCommentChange("")
                            onRatingChange(0f)

                        },
                        text = R.string.rate,
                        modifier = Modifier
                            .padding(16.dp)
                            .weight(1f)
                    )
                }
            }
        },
        sheetPeekHeight = 0.dp

    ) {


        Scaffold(
            modifier = Modifier.fillMaxSize(),
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 16.dp),
                color = MaterialTheme.colorScheme.background,
            ) {
                if (expiredState.expiredBookings.isNotEmpty()) {
                    LazyColumn(
                        contentPadding = it,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(expiredState.expiredBookings) {
                            BookingCard(
                                bookingDetails = it,
                                bookingStatus = BookingStatus.EXPIRED,
                                onViewPlaygroundClick = {},
                                onClickPlaygroundCard = onClickPlayground,
                                toRate = {
                                    scope.launch {
                                        scaffoldState.bottomSheetState.expand()
                                    }

                                }
                            )
                        }
                    }
                } else {
                    EmptyScreen()
                }
            }
        }
    }
}