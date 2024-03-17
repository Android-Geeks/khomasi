package com.company.khomasi.presentation.components

import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun MyAlertDialog(
    @StringRes title: Int,
    @StringRes text: Int,
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable (() -> Unit)?,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(stringResource(title)) },
        text = { Text(stringResource(text)) },
        confirmButton = confirmButton,
        dismissButton = dismissButton,
        modifier = modifier
    )
}

