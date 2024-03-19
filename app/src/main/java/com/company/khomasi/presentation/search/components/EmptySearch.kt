package com.company.khomasi.presentation.search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.theme.darkText
import com.company.khomasi.theme.lightText

@Composable
fun EmptySearch(
    onClick: () -> Unit,
    isDark: Boolean
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(
                id = if (isDark) R.drawable.dark_empty_search
                else R.drawable.light_empty_search
            ),
            contentDescription = null
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Text(
            text = stringResource(id = R.string.no_matching_results),
            style = MaterialTheme.typography.titleMedium,
            color = if (isDark) darkText else lightText
        )
        Spacer(modifier = Modifier.padding(8.dp))
        MyButton(
            text = R.string.back_to_home,
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .imePadding()
        )
    }
}