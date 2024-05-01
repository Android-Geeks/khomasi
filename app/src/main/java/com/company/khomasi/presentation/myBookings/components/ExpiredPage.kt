package com.company.khomasi.presentation.myBookings.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.MyModalBottomSheet
import com.company.khomasi.presentation.components.MyTextField
import com.company.khomasi.presentation.components.cards.BookingCard
import com.company.khomasi.presentation.components.cards.BookingStatus
import com.company.khomasi.presentation.components.cards.RatingRow
import com.company.khomasi.presentation.myBookings.MyBookingUiState
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpiredPage(
    uiState: StateFlow<MyBookingUiState>,
    playgroundReview: () -> Unit,
    onCommentChange: (String) -> Unit,
    onRatingChange: (Float) -> Unit,
    reBook: (Int) -> Unit,

) {
    val expiredState = uiState.collectAsState().value
    val sheetState = rememberModalBottomSheetState()
    var isOpen by remember { mutableStateOf(false) }
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value) {
            scrollState.animateScrollTo(scrollState.maxValue, tween(300))
        }
    }

    if (isOpen) {
        MyModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                isOpen = false
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.rating),
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier.padding(top = 12.dp)
                )
                RatingRow(
                    rating = expiredState.rating,
                    onRatingChange = onRatingChange,
                )

                MyTextField(
                    value = expiredState.comment,
                    onValueChange = onCommentChange,
                    label = R.string.your_comment,
                    keyBoardType = KeyboardType.Text,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                MyButton(
                    onClick = {
                        playgroundReview()
                        onCommentChange(" ")
                        onRatingChange(0f)
                        isOpen = false
                    },
                    text = R.string.rate,
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f)
                )
            }
        }
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp),
        containerColor = MaterialTheme.colorScheme.background,
        ) {
                    LazyColumn(
                        contentPadding = it,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        if (expiredState.expiredBookings.isNotEmpty()) {
                            items(expiredState.expiredBookings) {
                            BookingCard(
                                bookingDetails = it,
                                bookingStatus = BookingStatus.EXPIRED,
                                onViewPlaygroundClick = {},
                                toRate = {
                                    isOpen = true
                                    it.playgroundId
                                },
                                reBook = { reBook(it.playgroundId) }
                            )
                        }
                    } else {
                            item { EmptyScreen() }
                        }
                }
            }
        }
