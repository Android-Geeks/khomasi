package com.company.khomasi.presentation.venues.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.MyOutlinedButton
import com.company.khomasi.presentation.venues.SelectedFilter


@Composable
fun PlaygroundsFilterSelection(
    choice: Int,
    onChoiceChange: (Int) -> Unit,
    onShowFiltersClicked: (SelectedFilter, String) -> Unit,
    onResetFilters: () -> Unit,
    bookingTime: String
) {
    val interactionSource = remember { MutableInteractionSource() }
    val choices = listOf(
        stringResource(id = R.string.rating),
        stringResource(id = R.string.nearest_to_me),
        stringResource(id = R.string.bookable)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {

        SelectedFilter.entries.forEach { filter ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        onChoiceChange(filter.ordinal)
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                RadioButton(
                    selected = filter.ordinal == choice,
                    onClick = {
                        onChoiceChange(filter.ordinal)
                    }
                )
                Text(
                    text = choices[filter.ordinal],
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        MyOutlinedButton(
            text = R.string.reset,
            onClick = {
                onResetFilters()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        MyButton(
            text = R.string.show_results,
            onClick = {
                onShowFiltersClicked(SelectedFilter.entries[choice], bookingTime)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }
}

//@Preview(showSystemUi = true)
//@Composable
//fun Prev() {
//    var choice by remember { mutableStateOf(0) }
//    KhomasiTheme {
//        PlaygroundsFilterSelection(
//            choice = choice,
//            onChoiceChange = { choice = it },
//            onFilterTypeChanged = {},
//            onResetFilters = {},
//            bookingTime = "",
//
//        )
//    }
//
//}