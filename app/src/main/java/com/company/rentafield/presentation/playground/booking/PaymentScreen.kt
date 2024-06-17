package com.company.rentafield.presentation.playground.booking

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.rentafield.R
import com.company.rentafield.domain.DataState
import com.company.rentafield.domain.model.BookingPlaygroundResponse
import com.company.rentafield.presentation.components.MyButton
import com.company.rentafield.presentation.components.MyTextField
import com.company.rentafield.presentation.playground.PaymentType
import com.company.rentafield.presentation.playground.PlaygroundUiState
import com.company.rentafield.theme.darkText
import com.company.rentafield.theme.lightText
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import java.util.Locale

@Composable
fun PaymentScreen(
    playgroundUiState: StateFlow<PlaygroundUiState>,
    bookingPlaygroundResponse: StateFlow<DataState<BookingPlaygroundResponse>>,
    context: Context = LocalContext.current,
    updateCardNumber: (String) -> Unit,
    updateCardValidationDate: (String) -> Unit,
    updateCardCvv: (String) -> Unit,
    onPayWithVisaClicked: () -> Unit,
    onPayWithCoinsClicked: () -> Unit,
    onBookingSuccess: () -> Unit,
    onBackClicked: () -> Unit
) {
    val bookingResponse by bookingPlaygroundResponse.collectAsStateWithLifecycle()
    val showFawryDialog = remember { mutableStateOf(true) }
    val scrollState = rememberScrollState()
    val uiState by playgroundUiState.collectAsStateWithLifecycle()
    var choice by remember { mutableIntStateOf(3) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val localFocusManager = LocalFocusManager.current
    val keyboardActions = KeyboardActions(
        onNext = { localFocusManager.moveFocus(FocusDirection.Down) },
        onDone = {
            keyboardController?.hide()
        }
    )

    val interactionSource = remember { MutableInteractionSource() }
    val choices = listOf(
        stringResource(id = R.string.visa),
        stringResource(id = R.string.fawry),
        stringResource(id = R.string.coins),
        ""
    )
    LaunchedEffect(bookingResponse) {
        when (bookingResponse) {
            is DataState.Success -> {
                Toast.makeText(
                    context,
                    "Booking Successful",
                    Toast.LENGTH_SHORT
                ).show()
                delay(1000)
                onBookingSuccess()
            }

            is DataState.Error -> {
                Toast.makeText(
                    context,
                    "Booking Failed",
                    Toast.LENGTH_SHORT
                ).show()
            }

            is DataState.Loading -> {

            }

            else -> {}
        }
    }
    Scaffold(
        topBar = {
            PaymentTopBar(onBackClicked = onBackClicked)
        },
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(vertical = 16.dp)
                    .fillMaxSize(),
            ) {
                Text(
                    text = stringResource(id = R.string.select_payment_method) + " :",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                PaymentType.entries.forEach { type ->
                    if (type.ordinal < 3) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null
                                ) {
                                    choice = type.ordinal
                                },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            RadioButton(
                                selected = type.ordinal == choice,
                                onClick = {
                                    choice = type.ordinal
                                }
                            )

                            Text(
                                text = choices[type.ordinal],
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }
                    }
                    if (type.ordinal == 0 && choice == 0) {
                        CardContent(
                            cardNum = uiState.cardNumber,
                            cardValidationDate = uiState.cardValidationDate,
                            cardCvv = uiState.cardCvv,
                            updateCardNumber = updateCardNumber,
                            updateCardValidationDate = updateCardValidationDate,
                            updateCardCvv = updateCardCvv,
                            onPayVisaClicked = onPayWithVisaClicked,
                            keyboardActions = keyboardActions
                        )
                    }
                    if (type.ordinal == 2 && choice == 2) {
                        CoinsContent(
                            coins = uiState.coins.toLong(),
                            price = uiState.totalCoinPrice.toLong(),
                            onPayWithCoinsClicked = onPayWithCoinsClicked
                        )
                    }
                    if (type.ordinal == 1 && choice == 1) {
                        if (showFawryDialog.value) {
                            FawryContent(
                                onConfirmButtonClick = { showFawryDialog.value = false },
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Image(
                    painter = painterResource(
                        if (isSystemInDarkTheme()) {
                            R.drawable.dark_starting_player
                        } else {
                            R.drawable.light_starting_player
                        }
                    ), contentDescription = null
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentTopBar(
    onBackClicked: () -> Unit = {},
) {
    val currentLanguage = Locale.getDefault().language
    Column(verticalArrangement = Arrangement.Center) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            Spacer(modifier = Modifier.width(4.dp))
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.payment_method),
                        style = MaterialTheme.typography.displayMedium,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBackClicked() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.back),
                            contentDescription = null,
                            Modifier
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
                },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.background)
            )
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(), thickness = 1.dp
        )
    }
}

@Composable
fun FawryContent(
    onConfirmButtonClick: () -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 16.dp, top = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.Left,
            ) {
                Text(
                    text = stringResource(R.string.fawry_pay),
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,

                    )
            }
            Box(
                modifier = Modifier.border(
                    color = MaterialTheme.colorScheme.primary,
                    width = 1.dp,
                    shape = MaterialTheme.shapes.medium
                )
            ) {
                Row(
                    Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.pay_with),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.fawry_icon),
                        contentDescription = null,
                        modifier = Modifier.size(150.dp, 60.dp),
                    )
                }
            }

            TextButton(
                onClick = onConfirmButtonClick,
                colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.process),
                    style = MaterialTheme.typography.titleLarge,
                    color = if (isSystemInDarkTheme()) lightText else darkText
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }


}

@Composable
fun CoinsContent(
    coins: Long,
    price: Long,
    onPayWithCoinsClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 16.dp, top = 16.dp),
            horizontalArrangement = Arrangement.Absolute.Left,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.currencycircledollar),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = stringResource(R.string.coin_pay),
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Start
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy((-4).dp)
        ) {
            Text(
                text = stringResource(id = R.string.balance),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline,
            )
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = String.format("%d", coins),
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
                Text(
                    text = stringResource(id = R.string.coin),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )

            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.booking_price),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline,
            )
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = String.format("%d", price),
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
                Text(
                    text = stringResource(id = R.string.coin),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )

            }

            MyButton(
                text = R.string.pay,
                onClick = onPayWithCoinsClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
        }
    }
}

@Composable
fun CardContent(
    cardNum: String,
    cardValidationDate: String,
    cardCvv: String,
    updateCardNumber: (String) -> Unit,
    updateCardValidationDate: (String) -> Unit,
    updateCardCvv: (String) -> Unit,
    onPayVisaClicked: () -> Unit,
    keyboardActions: KeyboardActions,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MyTextField(
                value = cardNum,
                onValueChange = updateCardNumber,
                label = R.string.card_number,
                placeholder = stringResource(R.string.xxxx_xxxx),
                keyBoardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
                keyboardActions = keyboardActions,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(modifier = Modifier.weight(2f)) {
                    MyTextField(
                        value = cardValidationDate,
                        onValueChange = updateCardValidationDate,
                        label = R.string.validation_date,
                        placeholder = stringResource(id = R.string.card_date),
                        keyBoardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                        keyboardActions = keyboardActions,
                    )
                }
                Row(modifier = Modifier.weight(1f)) {
                    MyTextField(
                        value = cardCvv,
                        onValueChange = updateCardCvv,
                        label = R.string.card_cvv,
                        placeholder = "xxx",
                        keyBoardType = KeyboardType.Number,
                        imeAction = ImeAction.Done,
                        keyboardActions = keyboardActions,
                    )
                }
            }
            MyButton(
                text = R.string.pay,
                onClick = onPayVisaClicked,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

//@SuppressLint("UnrememberedMutableState")
//@Preview(showSystemUi = true)
//@Composable
//fun PaymentScreenPreview() {
//    KhomasiTheme {
//        PaymentScreen(
//            playgroundUiState = MutableStateFlow(PlaygroundUiState()),
//            bookingPlaygroundResponse = MutableStateFlow(DataState.Success(BookingPlaygroundResponse())),
//            onPayWithVisaClicked = {},
//            updateCardNumber = {},
//            updateCardValidationDate = {},
//            updateCardCvv = {},
//            onPayWithCoinsClicked = {},
//            onBackClicked = {}
//        )
//    }
//}