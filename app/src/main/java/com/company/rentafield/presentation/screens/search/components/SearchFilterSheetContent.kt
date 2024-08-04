package com.company.rentafield.presentation.screens.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.company.rentafield.R
import com.company.rentafield.presentation.components.MyButton
import com.company.rentafield.presentation.screens.search.SearchFilter
import com.company.rentafield.theme.darkText
import com.company.rentafield.theme.lightText

@Composable
fun SearchFilterSheetContent(
    choice: Int,
    onChoiceChange: (Int) -> Unit,
    onSearchFilterChanged: (SearchFilter) -> Unit,
    hideBottomSheet: () -> Unit,
    isDark: Boolean
) {
    val interactionSource = remember { MutableInteractionSource() }
    val choices = listOf(
        stringResource(id = R.string.lowest_price),
        stringResource(id = R.string.rating),
        stringResource(id = R.string.nearest_to_me),
        stringResource(id = R.string.bookable)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.sort_by),
            color = if (isDark) darkText else lightText,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.padding(4.dp))
        SearchFilter.entries.forEach { filter ->
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
        Spacer(modifier = Modifier.padding(4.dp))
        MyButton(
            text = R.string.save, onClick = {
                onSearchFilterChanged(SearchFilter.entries[choice])
                hideBottomSheet()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }
}