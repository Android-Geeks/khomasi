package com.company.khomasi.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.InternalComposeApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasi.theme.KhomasiTheme

@Composable
@InternalComposeApi
fun MyTextField(
    // leadingIcon : Painter,
    value: String,
    onValueChange: (String) -> Unit,
    // @StringRes text : Int,
    prefixTxt: String? = null,
    text: String,
    placeholder: String? = null,
    keyBoardType: KeyboardType,
    imAction: ImeAction = ImeAction.Done,
    onDone: () -> Unit,
    modifier: Modifier = Modifier

) {
    Column {
        Text(text, textAlign = TextAlign.Start)
        Spacer(modifier = modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,

//            leadingIcon =  {
//                Icon(painter = leadingIcon,
//                    contentDescription = null) },
            prefix = { prefixTxt?.let { Text(text = prefixTxt) } },
            modifier = Modifier
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
@InternalComposeApi
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: Painter,
    text: String,
    placeholder: String? = null,
    keyBoardType: KeyboardType = KeyboardType.NumberPassword,
    imAction: ImeAction = ImeAction.Done,
    onDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column {
        Text(text, textAlign = TextAlign.Start)
        Spacer(modifier = modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            leadingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = if (passwordVisible) leadingIcon else leadingIcon,
                        contentDescription = null,
                    )
                }
            },
            modifier = modifier
                .width(358.dp)
                .height(82.dp)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            placeholder = { placeholder?.let { Text(text = it) } },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyBoardType,
                imeAction = imAction
            ),
            keyboardActions = KeyboardActions(onDone = { onDone() }),
            visualTransformation = if (passwordVisible) {
                VisualTransformation.None
            } else PasswordVisualTransformation()

        )
    }
}

@Composable
@InternalComposeApi
fun TextFieldWithoutIcon(
    value: String,
    onValueChange: (String) -> Unit,
    // @StringRes text : Int,
    text: String,
    placeholder: String? = null,
    keyBoardType: KeyboardType,
    imAction: ImeAction = ImeAction.Done,
    onDone: () -> Unit,
    modifier: Modifier = Modifier

) {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(text, textAlign = TextAlign.Start)
        // Spacer(modifier = modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            modifier = Modifier
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

@OptIn(InternalComposeApi::class)
@Preview(name = "night", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewTextField() {
    KhomasiTheme {
        var text by remember {
            mutableStateOf(" ")
        }
        Column {

            TextFieldWithoutIcon(
                value = text,
                onValueChange = { text = it },
                text = "sample",
                keyBoardType = KeyboardType.Text,
                onDone = { /*TODO*/ })
            MyTextField(
                value = text,
                onValueChange = { text = it },
                text = "sample",
                keyBoardType = KeyboardType.Number,
                onDone = { /*TODO*/ })
        }
    }
}