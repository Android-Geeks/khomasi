package com.company.rentafield.presentation.venues.component

import android.content.res.Configuration
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.rentafield.R
import com.company.rentafield.presentation.components.MyButton
import com.company.rentafield.theme.KhomasiTheme
import com.company.rentafield.theme.darkText
import com.company.rentafield.theme.lightText

@Composable
fun EmptyResult(
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
            text = R.string.reset_filter,
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .imePadding()
        )
    }
}

@Preview(name = "light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun EmptyResultPreview() {
    KhomasiTheme {
        EmptyResult({}, false)
    }
}