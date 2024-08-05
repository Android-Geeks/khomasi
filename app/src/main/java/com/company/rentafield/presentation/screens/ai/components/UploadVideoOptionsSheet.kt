package com.company.rentafield.presentation.screens.ai.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.company.rentafield.R
import com.company.rentafield.presentation.components.MyButton
import com.company.rentafield.presentation.components.MyModalBottomSheet
import com.company.rentafield.presentation.components.MyOutlinedButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadVideoOptionsSheet(
    bottomSheetState: SheetState,
    onDismissRequest: () -> Unit,
    onCaptureVideo: () -> Unit,
    onChooseFromGallery: () -> Unit,
) {
    MyModalBottomSheet(
        sheetState = bottomSheetState,
        onDismissRequest = onDismissRequest,
        modifier = Modifier
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            MyOutlinedButton(
                text = R.string.capture_video,
                onClick = {
                    onDismissRequest()
                    onCaptureVideo()
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth()
            )
            MyButton(
                text = R.string.choose_from_gallery,
                onClick = {
                    onDismissRequest()
                    onChooseFromGallery()
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}