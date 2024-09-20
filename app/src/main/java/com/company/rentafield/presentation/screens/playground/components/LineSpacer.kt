package com.company.rentafield.presentation.screens.playground.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LineSpacer() {
    HorizontalDivider(
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.outline,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}