package com.company.khomasi.presentation.playground

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.theme.darkText
import com.company.khomasi.theme.lightText

@Composable
fun PlaygroundRules(
    rulesList: List<String>
) {
    val myHeight = (30.dp * rulesList.size).coerceAtLeast(40.dp)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(myHeight)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.field_instructions),
            style = MaterialTheme.typography.titleLarge,
            color = if (isSystemInDarkTheme()) darkText else lightText
        )

        for (i in rulesList.indices.take(6)) {
            Text(
                text = " ${i + 1}. ${rulesList[i]}",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.tertiary,
            )
        }
    }
}