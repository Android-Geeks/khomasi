package com.company.rentafield.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.company.rentafield.R
import com.company.rentafield.presentation.theme.darkOverlay
import com.company.rentafield.presentation.theme.lightOverlay

@Composable
fun MyAlertDialog(
    @StringRes title: Int,
    @StringRes text: Int,
    onDismissRequest: () -> Unit,
    onConfirmButtonClick: () -> Unit,
    confirmButtonColor: Color,
    modifier: Modifier = Modifier,
    @StringRes confirmButtonText: Int = 0,
    dismissButton: @Composable (() -> Unit)? = null,
    isDark: Boolean = isSystemInDarkTheme()
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = stringResource(title),
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        },
        text = {
            Text(
                text = stringResource(text),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.tertiary
            )
        },
        confirmButton = {
            TextButton(
                onClick = onConfirmButtonClick,
                colors = ButtonDefaults.textButtonColors(
                    containerColor = confirmButtonColor,
                )
            ) {
                Text(
                    text = stringResource(id = if (confirmButtonText == 0) R.string.clear else confirmButtonText),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        },
        dismissButton = dismissButton,
        modifier = modifier.shadow(
            elevation = 8.dp,
            shape = MaterialTheme.shapes.medium
        ),
        containerColor = if (isDark) darkOverlay else lightOverlay,
        shape = RoundedCornerShape(12.dp)
    )
}

