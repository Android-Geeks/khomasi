package com.company.khomasi.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.theme.KhomasiTheme

@Composable
fun MyButton(
    text: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int = 0,
    shape: CornerBasedShape = MaterialTheme.shapes.large,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
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
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}


@Composable
fun MyTextButton(
    text: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isUnderlined: Boolean = true,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Text(
            text = stringResource(id = text),
            textAlign = TextAlign.Center,
            textDecoration = if (isUnderlined) TextDecoration.Underline else TextDecoration.None
        )
    }
}


@Composable
fun MyOutlinedButton(
    text: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int = 0,
    shape: CornerBasedShape = MaterialTheme.shapes.large,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
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
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Preview(name = "Night", uiMode = UI_MODE_NIGHT_YES)
@Preview(name = "Light", uiMode = UI_MODE_NIGHT_NO)
@Composable
fun ButtonPreview() {
    KhomasiTheme {
        Column {
            MyButton(
                text = R.string.skip,
                onClick = { },
                icon = R.drawable.clock
            )
            MyOutlinedButton(
                text = R.string.skip,
                onClick = { },
                icon = R.drawable.clock
            )
            MyTextButton(
                text = R.string.skip,
                onClick = { },
            )
        }
    }
}