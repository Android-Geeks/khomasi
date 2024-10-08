package com.company.rentafield.presentation.screens.otp

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.rentafield.presentation.theme.RentafieldTheme

@Composable
fun CodeTextField(
    value: String,
    length: Int,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    keyboardActions: KeyboardActions = KeyboardActions(onDone = {}),
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle,
) {
    val focusRequester = remember { FocusRequester() }

    DisposableEffect(Unit) {
        focusRequester.requestFocus()
        onDispose { keyboardController?.hide() }
    }

    BasicTextField(
        modifier = modifier.focusRequester(focusRequester),
        value = value,
        singleLine = true,
        onValueChange = {
            if (it.length <= length) {
                onValueChange(it)
            }
        },
        enabled = enabled,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        decorationBox = {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                repeat(length) { index ->
                    val ind = if (LocalLayoutDirection.current == LayoutDirection.Rtl)
                        length - 1 - index
                    else index

                    val currentChar = value.getOrNull(ind)

                    Box(
                        modifier = modifier
                            .size(56.dp)
                            .border(
                                width = 1.dp,
                                color = if (currentChar != null) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(8.dp),
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (currentChar != null) {
                            Text(
                                modifier = Modifier.fillMaxSize(),
                                text = currentChar.toString(),
                                textAlign = TextAlign.Center,
                                style = textStyle
                            )
                        }

                    }
                }
            }
        }
    )
}

@Preview(
    name = "DARK | EN",
    locale = "en",
    uiMode = UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF0E0E0E,
    showBackground = true
)
@Preview(
    name = "LIGHT | AR",
    locale = "ar",
    uiMode = UI_MODE_NIGHT_NO,
    backgroundColor = 0xFFF5F5F5,
    showBackground = true
)
@Composable
fun CodeTextFieldPreview() = RentafieldTheme {
    var code by remember { mutableStateOf("") }
    Column {
        CodeTextField(
            value = code,
            length = 5,
            onValueChange = {
                code = it
            },
            textStyle = MaterialTheme.typography.displayLarge.copy(
                fontSize = 28.sp,
                fontWeight = FontWeight(700)
            )
        )
    }
}