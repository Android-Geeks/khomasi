package com.company.rentafield.presentation.myBookings.components

import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.company.rentafield.R
import com.company.rentafield.presentation.components.MyButton
import com.company.rentafield.presentation.components.MyModalBottomSheet
import com.company.rentafield.presentation.components.MyTextField
import com.company.rentafield.presentation.components.cards.BookingCard
import com.company.rentafield.presentation.components.cards.BookingStatus
import com.company.rentafield.presentation.components.cards.RatingRow
import com.company.rentafield.presentation.myBookings.MyBookingUiState
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpiredPage(
    uiState: StateFlow<MyBookingUiState>,
    playgroundReview: () -> Unit,
    onCommentChange: (String) -> Unit,
    onRatingChange: (Float) -> Unit,
    reBook: (Int, Boolean) -> Unit,
    onClickBookField: () -> Unit,
    toRate: (Int) -> Unit,
) {
    val expiredState = uiState.collectAsState().value
    val sheetState = rememberModalBottomSheetState()
    var isOpen by remember { mutableStateOf(false) }
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    var rate by remember { mutableStateOf(false) }
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
                    onRatingChange = {
                        onRatingChange(it)
                        rate = true
                    },
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
                        if (rate) {
                            playgroundReview()
                            onCommentChange("")
                            onRatingChange(0f)
                            isOpen = false
                            Toast.makeText(
                                context,
                                context.getString(R.string.rating_sent), Toast.LENGTH_SHORT
                            ).show()

                        } else {
                            Toast.makeText(
                                context,
                                context.getString(R.string.rate_not_found), Toast.LENGTH_SHORT
                            ).show()
                            isOpen = true

                        }
                    },
                    text = R.string.rate,
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f)
                )
            }
        }
    }
    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {}
        items(expiredState.expiredBookings) { bookingDetails ->
            if (expiredState.expiredBookings.isNotEmpty()) {
                BookingCard(
                    bookingDetails = bookingDetails,
                    bookingStatus = BookingStatus.EXPIRED,
                    onViewPlaygroundClick = {
                        reBook(
                            bookingDetails.playgroundId,
                            bookingDetails.isFavorite
                        )
                    },
                    toRate = {
                        toRate(bookingDetails.playgroundId)
                        isOpen = true
                    },
                    reBook = { reBook(bookingDetails.playgroundId, bookingDetails.isFavorite) }
                )
            } else {
                EmptyScreen(
                    onClickBookField = onClickBookField
                )
            }
        }

    }
}
