package com.company.rentafield.presentation.screens.booking.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.ui.tooling.preview.Preview
import com.company.rentafield.R
import com.company.rentafield.presentation.theme.RentafieldTheme

@Composable
fun DurationSelection(
    updateDuration: (String) -> Unit,
    duration: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        DurationButton(updateDuration, "+", R.drawable.pluscircle)

        Text(
            text = "$duration ${stringResource(R.string.min)}",
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.tertiary,
        )
        DurationButton(updateDuration, "-", R.drawable.minuscircle, duration > 60)
    }
}

@Composable
fun DurationButton(
    updateDuration: (String) -> Unit,
    type: String,
    icon: Int,
    enabled: Boolean = true
) {
    IconButton(onClick = { updateDuration(type) }, enabled = enabled) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary.copy(alpha = if (enabled) 1f else 0.5f),
        )
    }
}

@Preview(
    name = "DARK | EN",
    locale = "en",
    uiMode = UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF0E0E0E,
    showBackground = true
)
@Preview(
    name = "LIGHT | AR",
    locale = "ar",
    uiMode = UI_MODE_NIGHT_NO,
    backgroundColor = 0xFFF5F5F5,
    showBackground = true
)
@Composable
fun DurationSelectionPreview() {
    RentafieldTheme {
        DurationSelection(updateDuration = {}, duration = 60)
    }
}
