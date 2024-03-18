package com.company.khomasi.presentation.search.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.theme.darkHint
import com.company.khomasi.theme.darkIcon
import com.company.khomasi.theme.lightHint
import com.company.khomasi.theme.lightIcon

@Composable
fun CustomSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    enabled: Boolean = true,
    isDark: Boolean = isSystemInDarkTheme(),
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        textStyle = MaterialTheme.typography.bodyMedium,
        enabled = enabled,
        colors = TextFieldDefaults.colors(
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            keyboardController?.hide()
            onSearch(query)
        }),
        singleLine = true,
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.magnifyingglass),
                contentDescription = null,
                tint = if (isDark) darkIcon else lightIcon,
                modifier = Modifier.size(24.dp)
            )
        },
        placeholder = {
            Text(
                text = stringResource(id = R.string.search_for),
                color = if (isDark) darkHint else lightHint,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        trailingIcon =
        if (query.isNotEmpty()) {
            {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        painter = painterResource(id = R.drawable.xcircle),
                        contentDescription = null,
                        tint = if (isDark) darkIcon else lightIcon,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        } else null,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(56.dp)
    )
}

@Preview
@Composable
fun CustomSearchBarPreview() {
    KhomasiTheme {
        CustomSearchBar(
            query = "Search",
            onQueryChange = {},
            onSearch = {},
        )
    }
}