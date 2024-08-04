package com.company.rentafield.presentation.screens.search.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.company.rentafield.R
import com.company.rentafield.theme.darkHint
import com.company.rentafield.theme.darkIcon
import com.company.rentafield.theme.darkText
import com.company.rentafield.theme.lightHint
import com.company.rentafield.theme.lightIcon
import com.company.rentafield.theme.lightText

@Composable
fun SearchTopAppBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onClearFocus: () -> Unit,
    hideKeyboard: Boolean,
    onBackClick: () -> Unit,
    isDark: Boolean,
    layoutDirection: LayoutDirection = LocalLayoutDirection.current,
) {
    Column {
        MyTopAppBar(
            title = R.string.search_for_field,
            onBackClick = onBackClick,
            isDark = isDark,
            layoutDirection = layoutDirection
        )

        CustomSearchBar(
            query = query,
            onQueryChange = onQueryChange,
            onSearch = onSearch,
            hideKeyboard = hideKeyboard,
            onFocusClear = onClearFocus,
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            thickness = 1.dp,
            color = if (isDark) darkHint else lightHint
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    @StringRes title: Int,
    onBackClick: () -> Unit,
    layoutDirection: LayoutDirection = LocalLayoutDirection.current,
    isDark: Boolean,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(title = {
        Text(
            text = stringResource(id = title),
            color = if (isDark) darkText else lightText,
            style = MaterialTheme.typography.displayMedium
        )
    }, navigationIcon = {
        IconButton(onClick = onBackClick) {
            Icon(
                painter = painterResource(id = R.drawable.back),
                contentDescription = stringResource(id = R.string.back),
                modifier = if (layoutDirection == LayoutDirection.Ltr)
                    Modifier.rotate(180f)
                else Modifier,
                tint = if (isDark) darkIcon else lightIcon
            )
        }
    }, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.background
    ),
        actions = actions
    )
}