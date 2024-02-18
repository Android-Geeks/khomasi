package com.company.khomasi.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasi.theme.KhomasiTheme

@Composable
fun MyButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int = 0,
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Row (verticalAlignment = Alignment.CenterVertically){
            if (icon != 0) {
                Icon(painter = painterResource(id = icon), contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
            }
            Text(text = text, textAlign = TextAlign.Center)
        }
    }
}


@Composable
fun MyTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            textDecoration = TextDecoration.Underline
        )
    }
}


@Composable
fun MyOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int = 0,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
    )
    {
        Row (verticalAlignment = Alignment.CenterVertically){
            if (icon != 0) {
                Icon(painter = painterResource(id = icon), contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
            }
            Text(
                text = text,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Preview(name = "night", uiMode = UI_MODE_NIGHT_YES)
@Preview(name = "light", uiMode = UI_MODE_NIGHT_NO)
@Composable
fun ButtonPreview() {
    KhomasiTheme {
        Column {
            MyButton(
                text = "Button",
                onClick = { },
            )
            MyOutlinedButton(
                text = "Button",
                onClick = { },
            )
            MyTextButton(
                text = "Button",
                onClick = { },
            )
        }
    }
}