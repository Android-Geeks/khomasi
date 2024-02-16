package com.company.khomasi.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasi.theme.KhomasiTheme

@Composable
fun button(
    text: String,
    onClick: () -> Unit,
    icon: Painter? = null,
    modifier: Modifier = Modifier
) {
    var isClicked by remember { mutableStateOf(false) }
    val buttonColor = if (isClicked) Color(0xFF00B562) else Color(0x8000B562)

    Button(
        onClick = {
            onClick()
            isClicked = !isClicked
        },
        modifier = modifier.padding(horizontal = 34.dp),
        colors = ButtonDefaults.buttonColors(buttonColor)

    ) {
        Row {
            icon?.let {
                Icon(painter = it, contentDescription = null)
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = text, textAlign = TextAlign.Center)
        }
    }

}


@Composable
fun txtButton(
    text: String,
    onClick: () -> Unit,
    icon: Painter? = null,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = onClick,
        modifier = modifier.padding(horizontal = 34.dp),
    ) {
        Row {
            icon?.let {
                Icon(painter = it, contentDescription = null)
            }
            Spacer(modifier = Modifier.width(4.dp))

            Text(text = text, textAlign = TextAlign.Center)
        }

    }

}


@Composable
fun outlinedButton(
    text: String,
    onClick: () -> Unit,
    icon: Painter? = null,
    modifier: Modifier = Modifier
) {

    OutlinedButton(
        onClick = onClick,
        modifier = modifier.padding(horizontal = 34.dp)
    ) {
        Row {
            icon?.let {
                Icon(painter = it, contentDescription = null)
            }
            Spacer(modifier = Modifier.width(4.dp))

            Text(text = text, textAlign = TextAlign.Center, color = Color.White)
        }
    }

}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview
@Composable
fun ButtonPreview() {
    KhomasiTheme {
        Column {
            button(
                text = "Button",
                onClick = { },
                icon = null
            )
            outlinedButton(
                text = "Button",
                onClick = { },
                icon = null
            )
            txtButton(
                text = "Button",
                onClick = { },
                icon = null
            )
        }
    }
}