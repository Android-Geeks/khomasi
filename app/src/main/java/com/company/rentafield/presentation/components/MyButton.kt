package com.company.rentafield.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerBasedShape
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.rentafield.R
import com.company.rentafield.presentation.theme.RentafieldTheme

@Composable
fun MyButton(
    text: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int = 0,
    shape: CornerBasedShape = MaterialTheme.shapes.medium,
    buttonEnable: Boolean = true,
    color: ButtonColors? = null,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        enabled = buttonEnable,
        colors = color ?: ButtonDefaults.buttonColors()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icon != 0) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            Text(
                text = stringResource(id = text),
                textAlign = TextAlign.Center,
                style = textStyle
            )
        }
    }
}


@Composable
fun MyTextButton(
    text: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colorScheme.primary,
    isUnderlined: Boolean = true,
    shape: CornerBasedShape = MaterialTheme.shapes.medium,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        shape = shape
    ) {
        Text(
            text = stringResource(id = text),
            textAlign = TextAlign.Center,
            color = textColor,
            textDecoration = if (isUnderlined) TextDecoration.Underline else TextDecoration.None,
            style = MaterialTheme.typography.titleLarge,
        )
    }
}


@Composable
fun MyOutlinedButton(
    text: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int = 0,
    shape: CornerBasedShape = MaterialTheme.shapes.medium,
    color: Color = MaterialTheme.colorScheme.primary,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        border = BorderStroke(1.dp, color),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icon != 0) {
                Icon(painter = painterResource(id = icon), contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
            }
            Text(
                text = stringResource(id = text),
                textAlign = TextAlign.Center,
                color = color,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Preview(name = "Night", uiMode = UI_MODE_NIGHT_YES)
@Preview(name = "Light", uiMode = UI_MODE_NIGHT_NO)
@Composable
fun ButtonPreview() {
    RentafieldTheme {
        Column {
            MyButton(
                text = R.string.skip,
                onClick = { },
                icon = R.drawable.clock,
            )
            MyOutlinedButton(
                text = R.string.skip,
                onClick = { },
                icon = R.drawable.clock,
            )
            MyTextButton(
                text = R.string.skip,
                onClick = { },
            )
        }
    }
}