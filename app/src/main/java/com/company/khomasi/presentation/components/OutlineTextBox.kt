package com.company.khomasi.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.InternalComposeApi
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign

@Composable
@InternalComposeApi
fun TextField(
    leadingIcon : Painter,
    value : String ,
    onValueChange : (String) -> Unit,
    @StringRes text : Int,
    placeholder: String?=null,
    keyBoardType : KeyboardType ,
    imAction : ImeAction = ImeAction.Done,
    onDone : () -> Unit,
    modifier : Modifier = Modifier

    ) {
    Column {
        Text(text = stringResource(id = text), textAlign = TextAlign.Start)
        Spacer(modifier = modifier.height(6.dp))
        OutlinedTextField(value = value,
            onValueChange =onValueChange,
            singleLine = true,
            leadingIcon =  {
                Icon(painter = leadingIcon,
                    contentDescription = null) },
            modifier = Modifier
                .width(358.dp)
                .height(82.dp)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            placeholder = {placeholder?.let { Text(text = placeholder)}},
            keyboardOptions = KeyboardOptions(
                keyboardType = keyBoardType,
                imeAction = imAction),
            keyboardActions = KeyboardActions(onDone = {onDone })

        )
    }

}

@Composable
@InternalComposeApi
fun TextFieldWithoutIcon(
    value : String ,
    onValueChange : (String) -> Unit,
    @StringRes text : Int,
    placeholder: String?=null,
    keyBoardType : KeyboardType ,
    imAction : ImeAction = ImeAction.Done,
    onDone : () -> Unit,
    modifier : Modifier = Modifier

) {
    Column {
        Text(text = stringResource(id = text), textAlign = TextAlign.Start)
        Spacer(modifier = modifier.height(6.dp))
        OutlinedTextField(value = value,
            onValueChange =onValueChange,
            singleLine = true,
            modifier = Modifier
                .width(358.dp)
                .height(82.dp)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            placeholder = {placeholder?.let {
                Text(text = placeholder)}
                          },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyBoardType,
                imeAction = imAction),
            keyboardActions = KeyboardActions(onDone = { onDone})

        )
    }

}
