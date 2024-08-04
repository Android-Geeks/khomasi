package com.company.rentafield.presentation.screens.myBookings.components

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.rentafield.R
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.MessageResponse
import com.company.rentafield.domain.model.booking.MyBookingsResponse
import com.company.rentafield.presentation.components.MyButton
import com.company.rentafield.presentation.components.MyModalBottomSheet
import com.company.rentafield.presentation.components.MyTextField
import com.company.rentafield.presentation.components.cards.BookingCard
import com.company.rentafield.presentation.components.cards.BookingStatus
import com.company.rentafield.presentation.components.cards.RatingRow
import com.company.rentafield.presentation.components.connectionStates.ThreeBounce
import com.company.rentafield.presentation.screens.myBookings.MyBookingUiState
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpiredPage(
    bookingsUiState: StateFlow<MyBookingUiState>,
    myBookingsState: StateFlow<DataState<MyBookingsResponse>>,
    reviewState: StateFlow<DataState<MessageResponse>>,
    playgroundReview: () -> Unit,
    onCommentChange: (String) -> Unit,
    onRatingChange: (Float) -> Unit,
    reBook: (Int, Boolean) -> Unit,
    onClickBookField: () -> Unit,
) {
    val expiredState by bookingsUiState.collectAsStateWithLifecycle()
    val bookingsState by myBookingsState.collectAsStateWithLifecycle()
    val ratingState by reviewState.collectAsStateWithLifecycle()
    var showLoading by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    var isOpen by remember { mutableStateOf(false) }
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value) {
            scrollState.animateScrollTo(scrollState.maxValue, tween(300))
        }
    }
    Log.d("GoingToHave", ratingState.toString())
    LaunchedEffect(bookingsState) {
        when (bookingsState) {
            is DataState.Success -> {
                showLoading = false
            }

            DataState.Loading -> {
                showLoading = true
            }

            is DataState.Error -> {
                showLoading = false
                Toast.makeText(context, R.string.booking_failure, Toast.LENGTH_SHORT).show()
            }

            DataState.Empty -> {}
        }
    }
    LaunchedEffect(ratingState) {
        when (val state = ratingState) {
            is DataState.Success -> {
                Toast.makeText(
                    context,
                    context.getString(R.string.rating_sent), Toast.LENGTH_SHORT
                ).show()
            }

            DataState.Loading -> {

            }

            is DataState.Error -> {
                if (state.code == 400)
                    Toast.makeText(context, R.string.already_rated, Toast.LENGTH_SHORT).show()
                else {
                    Toast.makeText(context, R.string.rating_failure, Toast.LENGTH_SHORT).show()
                }
            }

            DataState.Empty -> {}
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
                        playgroundReview()
                        isOpen = false
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
    }
    Box {
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
        if (showLoading) {
            ThreeBounce(modifier = Modifier.fillMaxSize())
        }
    }
}
