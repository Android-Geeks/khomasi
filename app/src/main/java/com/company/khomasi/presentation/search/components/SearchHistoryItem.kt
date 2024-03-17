package com.company.khomasi.presentation.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.theme.darkSubText
import com.company.khomasi.theme.lightSubText

@Composable
fun SearchHistoryItem(
    text: String,
    onClick: () -> Unit,
    isDark: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable {
                onClick()
            }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.clock),
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = text,
            color = if (isDark) darkSubText else lightSubText,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}