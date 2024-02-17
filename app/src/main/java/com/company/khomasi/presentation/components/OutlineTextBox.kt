package com.company.khomasi.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.company.khomasi.R

@Composable
fun TextField(
    leadingIcon: Painter,
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes text: Int,
    onDone: () -> Unit,
    keyBoardType: KeyboardType,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    imAction: ImeAction = ImeAction.Done,
) {
    Column {
        Text(text = stringResource(id = text), textAlign = TextAlign.Start)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            leadingIcon = {
                Icon(
                    painter = leadingIcon,
                    contentDescription = null
                )
            },
            modifier = modifier
                .width(358.dp)
                .height(82.dp)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            placeholder = { placeholder?.let { Text(text = placeholder) } },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyBoardType,
                imeAction = imAction
            ),
            keyboardActions = KeyboardActions(onDone = { onDone() })
        )
    }

}

@Composable
fun TextFieldWithoutIcon(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes text: Int,
    keyBoardType: KeyboardType,
    onDone: () -> Unit,
    modifier: Modifier = Modifier,
    imAction: ImeAction = ImeAction.Done,
    placeholder: String? = null,
) {
    Column {
        Text(text = stringResource(id = text), textAlign = TextAlign.Start)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            modifier = modifier
                .width(358.dp)
                .height(82.dp)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            placeholder = {
                placeholder?.let {
                    Text(text = placeholder)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyBoardType,
                imeAction = imAction
            ),
            keyboardActions = KeyboardActions(onDone = { onDone() })
        )
    }

}


@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun TextFieldPreview() {
    Column {
        TextField(
            leadingIcon = painterResource(id = R.drawable.heart),
            value = "text",
            onValueChange = { },
            text = R.string.email,
            placeholder = "Enter your email",
            keyBoardType = KeyboardType.Text,
            onDone = { /* handle done action */ },
            modifier = Modifier
        )
        TextFieldWithoutIcon(
            value = "text",
            onValueChange = { },
            text = R.string.password,
            placeholder = "Enter your password",
            keyBoardType = KeyboardType.Password,
            onDone = { /* handle done action */ },
            modifier = Modifier
        )
    }
}

