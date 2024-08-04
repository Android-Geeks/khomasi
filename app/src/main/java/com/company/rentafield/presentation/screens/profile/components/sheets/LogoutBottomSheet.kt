package com.company.rentafield.presentation.screens.profile.components.sheets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.rentafield.R
import com.company.rentafield.presentation.components.MyButton
import com.company.rentafield.presentation.components.MyModalBottomSheet
import com.company.rentafield.presentation.components.MyTextButton
import com.company.rentafield.theme.RentafieldTheme
import com.company.rentafield.theme.darkErrorColor
import com.company.rentafield.theme.darkText
import com.company.rentafield.theme.lightErrorColor
import com.company.rentafield.theme.lightText
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
    MyModalBottomSheet(
        sheetState = bottomSheetState,
        onDismissRequest = onDismissRequest,
        modifier = Modifier
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
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
                Row(
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    MyTextButton(
                        text = R.string.cancel,
                        onClick = {
                            scope.launch {
                                bottomSheetState.hide()
                                onDismissRequest()
                            }
                        },
                        textColor = if (isDark) darkText else lightText,
                        isUnderlined = false,
                        modifier = Modifier.weight(1f)
                    )
                    MyButton(
                        text = R.string.logout,
                        onClick = {
                            scope.launch {
                                logout()
                                bottomSheetState.hide()
                                onDismissRequest()
                            }
                        },
                        shape = MaterialTheme.shapes.medium,
                        color = ButtonDefaults.buttonColors(
                            containerColor = if (isDark) darkErrorColor else lightErrorColor
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun LogoutBottomSheetPreview() {
    RentafieldTheme {
        LogoutBottomSheet(
            bottomSheetState = rememberModalBottomSheetState(),
            onDismissRequest = {},
            scope = rememberCoroutineScope(),
            logout = {},
            isDark = false
        )
    }

}