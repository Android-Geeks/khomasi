package com.company.khomasi.presentation.ui.screens.otpScreen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewmodel.compose.viewModel
import com.company.khomasi.theme.KhomasiTheme

@Composable
fun SmsCodeView(
    smsCodeLength: Int,
    textFieldColors: TextFieldColors,
    textStyle: TextStyle,
    smsFulled: (String) -> Unit,
    modifier: Modifier = Modifier,
    otpViewModel: OtpViewModel= viewModel()
) {
    val enteredNumbers = remember {
        mutableStateListOf(*Array(smsCodeLength) { "" })
    }
    val focusRequesters: List<FocusRequester> = remember {
        (0 until smsCodeLength).map { FocusRequester() }
    }
//    val enteredNumbers = remember {
//        mutableStateListOf(
//            *((0 until smsCodeLength).map { "" }.toTypedArray())
//        )
//    }

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        for (index in 0 until smsCodeLength) {
            OutlinedTextField(
                modifier = modifier
                    .width(60.dp)
                    .height(60.dp)
                    .border(
                        width = 0.5.dp, color = Color.LightGray,
                        shape = MaterialTheme.shapes.small

                    )
                    .focusRequester(focusRequester = focusRequesters[index])
                    .onKeyEvent { keyEvent: KeyEvent ->
                        val currentValue = enteredNumbers.getOrNull(index) ?: ""
                        if (keyEvent.key == Key.Backspace) {
                            if (currentValue.isNotEmpty()) {
                                enteredNumbers[index] = ""
                                smsFulled.invoke(enteredNumbers.joinToString(separator = ""))

                            } else {
                                focusRequesters
                                    .getOrNull(index.minus(1))
                                    ?.requestFocus()
                            }
                        }
                        false
                    },

                textStyle = textStyle,
                singleLine = true,
                value = enteredNumbers.getOrNull(index)?.trim() ?: "",
                maxLines = 1,
                colors = textFieldColors,
                onValueChange = { value: String ->
                    when {
                        value.isDigitsOnly() -> {
                            if (focusRequesters[index].freeFocus()) {
                                when (value.length) {
                                    1 -> {
                                        enteredNumbers[index] = value.trim()
                                        smsFulled.invoke(enteredNumbers.joinToString(separator = ""))
                                        focusRequesters.getOrNull(index + 1)?.requestFocus()
                                    }

                                    2 -> {
                                        focusRequesters.getOrNull(index + 1)?.requestFocus()
                                    }

                                    else -> {
                                        return@OutlinedTextField
                                    }
                                }
                            }
                        }

                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                ),
            )
            val fulled = enteredNumbers.joinToString(separator = "")
            if (fulled.length == smsCodeLength) {
                smsFulled.invoke(fulled)
            }
            Spacer(modifier = modifier.width(16.dp))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun otpPreview() {
    val viewModel = OtpViewModel()
    KhomasiTheme {
        SmsCodeView(
            smsCodeLength = 5,
            textFieldColors = TextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.primary,
            ),
            textStyle = MaterialTheme.typography.displayLarge ,
            smsFulled ={"  "},
            otpViewModel = viewModel

        )
    }

}