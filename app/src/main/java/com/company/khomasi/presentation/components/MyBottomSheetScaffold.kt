package com.company.khomasi.presentation.components


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.runtime.Composable

import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.company.khomasi.theme.darkOverlay
import com.company.khomasi.theme.lightOverlay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBottomSheetScaffold(
    bottomSheetContent: @Composable () -> Unit,
    sheetContent: @Composable (PaddingValues) -> Unit,
    modifier: Modifier = Modifier,
    scaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
    sheetPeekHeight: Dp = LocalConfiguration.current.screenHeightDp.dp * 0.65f, // Adjust as needed
) {
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = sheetPeekHeight,
        sheetContent = {
            bottomSheetContent()
        },
        content = {
            sheetContent(PaddingValues(it.calculateTopPadding()))
        },
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContainerColor = if(isSystemInDarkTheme()) darkOverlay else lightOverlay,
        modifier = modifier
    )
}

