package com.company.khomasi.presentation.profile.components.sheets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.presentation.components.MyModalBottomSheet
import com.company.khomasi.theme.darkText
import com.company.khomasi.theme.lightText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadPhotoOptionsSheet(
    bottomSheetState: SheetState,
    onDismissRequest: () -> Unit,
    onTakePhoto: () -> Unit,
    onChooseFromGallery: () -> Unit,
    isDark: Boolean
) {
    MyModalBottomSheet(
        sheetState = bottomSheetState,
        onDismissRequest = onDismissRequest,
        modifier = Modifier
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    onDismissRequest()
                    onTakePhoto()
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(id = R.string.take_a_photo),
                    style = MaterialTheme.typography.titleLarge,
                    color = if (isDark) darkText else lightText
                )
            }
            Button(
                onClick = {
                    onDismissRequest()
                    onChooseFromGallery()
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(id = R.string.choose_from_gallery),
                    style = MaterialTheme.typography.titleLarge,
                    color = if (isDark) darkText else lightText
                )
            }
        }
    }
}