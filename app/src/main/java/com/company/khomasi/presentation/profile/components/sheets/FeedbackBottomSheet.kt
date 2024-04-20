package com.company.khomasi.presentation.profile.components.sheets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.MyModalBottomSheet
import com.company.khomasi.presentation.profile.FeedbackCategory
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.theme.darkText
import com.company.khomasi.theme.lightText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackBottomSheet(
    bottomSheetState: SheetState,
    selectedCategory: FeedbackCategory,
    onFeedbackSelected: (FeedbackCategory) -> Unit,
    feedback: String,
    scope: CoroutineScope,
    onFeedbackChanged: (String) -> Unit,
    onDismissRequest: () -> Unit,
    sendFeedback: () -> Unit,
    isDark: Boolean
) {
    var expandFeedbackCategory by remember { mutableStateOf(false) }
    val sheetHeight = LocalConfiguration.current.screenHeightDp.dp / 2

    MyModalBottomSheet(
        sheetState = bottomSheetState,
        onDismissRequest = onDismissRequest,
        modifier = Modifier
            .height(sheetHeight)
    ) {
        val feedbackCategories = listOf(
            R.string.suggestion,
            R.string.issue,
            R.string.complaint,
            R.string.other
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(id = R.string.share_your_feedback),
                style = MaterialTheme.typography.displayMedium,
                color = if (isDark) darkText else lightText
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                ExposedDropdownMenuBox(
                    expanded = expandFeedbackCategory,
                    onExpandedChange = {
                        expandFeedbackCategory = !expandFeedbackCategory
                    }
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = stringResource(
                            id = feedbackCategories[FeedbackCategory.values()
                                .indexOf(selectedCategory)]
                        ),
                        onValueChange = {},
                        label = {
                            Text(
                                text = stringResource(id = R.string.category),
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (isDark) darkText else lightText
                            )
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expandFeedbackCategory
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expandFeedbackCategory,
                        onDismissRequest = { expandFeedbackCategory = false }
                    ) {
                        feedbackCategories.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = stringResource(id = item),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = if (isDark) darkText else lightText
                                    )
                                },
                                onClick = {
                                    onFeedbackSelected(
                                        FeedbackCategory.values()[feedbackCategories.indexOf(item)]
                                    )
                                    expandFeedbackCategory = false
                                }
                            )
                        }
                    }
                }
            }
            OutlinedTextField(
                value = feedback,
                onValueChange = onFeedbackChanged,
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.write_here),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            MyButton(
                text = R.string.send,
                onClick = {
                    scope.launch {
                        sendFeedback()
                        bottomSheetState.hide()
                        onDismissRequest()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun FeedbackBottomSheetPreview() {
    KhomasiTheme {
        FeedbackBottomSheet(
            bottomSheetState = rememberModalBottomSheetState(),
            onDismissRequest = {},
            onFeedbackSelected = {},
            selectedCategory = FeedbackCategory.Suggestion,
            feedback = "",
            onFeedbackChanged = {},
            sendFeedback = {},
            scope = rememberCoroutineScope(),
            isDark = false
        )
    }

}