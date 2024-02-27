package com.company.khomasi.presentation.components


import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.company.khomasi.theme.darkOverlay
import com.company.khomasi.theme.lightOverlay


@Composable
fun AuthSheet(
    modifier: Modifier = Modifier,
    sheetModifier: Modifier = Modifier,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    screenContent: @Composable () -> Unit,
    sheetContent: @Composable () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        screenContent()
        Box(
            modifier = sheetModifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(if (isDarkTheme) darkOverlay else lightOverlay)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            sheetContent()
        }
    }
}