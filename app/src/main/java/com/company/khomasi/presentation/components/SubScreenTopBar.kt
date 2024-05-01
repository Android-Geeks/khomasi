package com.company.khomasi.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.company.khomasi.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubScreenTopBar(
    @StringRes title: Int,
    onBackClick: () -> Unit,
    navigationIcon: @Composable () -> Unit = {
        IconButton(
            onClick = onBackClick,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.back),
                modifier = if (LocalLayoutDirection.current == LayoutDirection.Ltr) Modifier.rotate(
                    180f
                ) else Modifier,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    },
    actions: @Composable RowScope.() -> Unit = {}
) {
    Column {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(title),
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.background),
            navigationIcon = navigationIcon,
            actions = actions
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(), thickness = 1.dp
        )
    }
}
