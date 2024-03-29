package com.company.khomasi.presentation.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.company.khomasi.theme.darkOverlay
import com.company.khomasi.theme.lightOverlay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyModalBottomSheet(
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        modifier = modifier,
        containerColor = if (isSystemInDarkTheme()) darkOverlay else lightOverlay
    ) {
        content()
    }
}