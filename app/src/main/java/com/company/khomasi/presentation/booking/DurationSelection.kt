package com.company.khomasi.presentation.booking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.company.khomasi.R

@Composable
fun DurationSelection(updateDuration: (String) -> Unit, duration: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = { updateDuration("+") }) {
            Icon(
                painter = painterResource(R.drawable.pluscircle),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Text(
            text = "$duration ${stringResource(R.string.min)}",
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.tertiary,
        )
        IconButton(
            onClick = { updateDuration("-") },
            enabled = duration > 60,
        ) {
            Icon(
                painter = painterResource(R.drawable.minuscircle),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary.copy(alpha = if (duration > 60) 1f else 0.5f),
            )
        }
    }
}