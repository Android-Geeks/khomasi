package com.company.khomasi.presentation.playground.booking

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.khomasi.R
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.MyTextField
import com.company.khomasi.presentation.playground.PaymentType
import com.company.khomasi.presentation.playground.PlaygroundUiState
import com.company.khomasi.theme.KhomasiTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@SuppressLint("RememberReturnType")
@Composable
fun PaymentScreen(
    playgroundUiState: StateFlow<PlaygroundUiState>,
    choice: Int,
    onChoiceChange: (Int) -> Unit,
    updateCardNumber: () -> Unit,
    updateCardValidationDate: () -> Unit,
    updateCardCvv: () -> Unit,
    onPayVisaClicked: () -> Unit,
) {
    val uiState by playgroundUiState.collectAsStateWithLifecycle()
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        PaymentType.entries.forEach { type ->
            if (type.ordinal < 3) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            onChoiceChange(type.ordinal)
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = type.ordinal == choice,
                        onClick = {
                            onChoiceChange(type.ordinal)
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
                    updateCardNumber = { updateCardNumber() },
                    updateCardValidationDate = { updateCardValidationDate() },
                    updateCardCvv = { updateCardCvv() },
                    onPayVisaClicked = onPayVisaClicked,
                    keyboardActions = keyboardActions
                )

            }
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
        Row(modifier = Modifier.fillMaxWidth()) {
            MyButton(
                text = R.string.pay,
                onClick = onPayVisaClicked,
                modifier = Modifier.weight(2f)
            )
            Spacer(modifier = Modifier.weight(2f))
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showSystemUi = true)
@Composable
fun PaymentScreenPreview() {
    KhomasiTheme {
        var choice by remember { mutableIntStateOf(3) }
        PaymentScreen(
            playgroundUiState = MutableStateFlow(PlaygroundUiState()),
            choice = choice,
            onChoiceChange = { choice = it },
            onPayVisaClicked = {},
            updateCardNumber = {},
            updateCardValidationDate = {},
            updateCardCvv = {}
        )
    }
}