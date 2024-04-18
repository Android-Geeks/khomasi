package com.company.khomasi.presentation.profile.components.sheets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.presentation.components.MyModalBottomSheet
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.theme.darkErrorColor
import com.company.khomasi.theme.darkText
import com.company.khomasi.theme.lightErrorColor
import com.company.khomasi.theme.lightText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogoutBottomSheet(
    bottomSheetState: SheetState,
    onDismissRequest: () -> Unit,
    scope: CoroutineScope,
    logout: () -> Unit,
    isDark: Boolean
) {
    val sheetHeight = LocalConfiguration.current.screenHeightDp.dp / 2

    MyModalBottomSheet(
        sheetState = bottomSheetState,
        onDismissRequest = onDismissRequest,
        modifier = Modifier
            .height(sheetHeight)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(id = R.string.logout),
                style = MaterialTheme.typography.displayMedium,
                color = if (isDark) darkText else lightText
            )
            Image(
                painter = painterResource(id = R.drawable.logout),
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(id = R.string.sad_to_see_you_go),
                    style = MaterialTheme.typography.displayLarge,
                    color = if (isDark) darkText else lightText
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.confirm_logout),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    TextButton(
                        onClick = {
                            scope.launch {
                                bottomSheetState.hide()
                                onDismissRequest()
                            }
                        },
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = stringResource(id = R.string.cancel),
                            style = MaterialTheme.typography.titleLarge,
                            color = if (isDark) darkText else lightText
                        )
                    }
                    Button(
                        onClick = {
                            scope.launch {
                                logout()
                                bottomSheetState.hide()
                                onDismissRequest()
                            }
                        },
                        shape = MaterialTheme.shapes.medium,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isDark) darkErrorColor else lightErrorColor
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = stringResource(id = R.string.logout),
                            style = MaterialTheme.typography.titleLarge,
                            color = if (isDark) darkText else lightText
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun LogoutBottomSheetPreview() {
    KhomasiTheme {
        LogoutBottomSheet(
            bottomSheetState = rememberModalBottomSheetState(),
            onDismissRequest = {},
            scope = rememberCoroutineScope(),
            logout = {},
            isDark = false
        )
    }

}