package com.company.khomasi.presentation.venues.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.khomasi.R
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.MyModalBottomSheet
import com.company.khomasi.presentation.components.MyOutlinedButton
import com.company.khomasi.theme.Cairo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetWarning(
    sheetState: SheetState,
    hideSheet: () -> Unit,
    onClickReset: () -> Unit
) {
    MyModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = hideSheet
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.padding(top = 12.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.info),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 4.dp, top = 8.dp)
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontFamily = Cairo,
                                fontWeight = FontWeight(500),
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            ),

                            )
                        {
                            append(stringResource(id = R.string.confirm_reset_filter) + "\n")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontFamily = Cairo,
                                fontWeight = FontWeight(500),
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        ) {
                            append(stringResource(id = R.string.action_will_reset_filter))
                        }
                    },
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                MyOutlinedButton(
                    onClick = { hideSheet() }, text = R.string.back,
                    modifier = Modifier.weight(1f)
                )
                MyButton(
                    onClick = {
                        onClickReset()
                        hideSheet()
                    },
                    text = R.string.reset,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f)
                )
            }
        }
    }
}
