package com.company.khomasi.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable

import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.theme.KhomasiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBottomSheetScaffold(
    bottomSheetContent: @Composable () -> Unit,
    scaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
    sheetPeekHeight: Dp = 64.dp, // Adjust as needed
    sheetContent: @Composable (PaddingValues) -> Unit
) {
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = sheetPeekHeight,
        sheetContent = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {
                bottomSheetContent()
            }
        },
        content = {
            sheetContent(PaddingValues(it.calculateTopPadding()))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScreen() {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val bottomPosition = remember { mutableStateOf(0f) }

    MyBottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = (LocalConfiguration.current.screenHeightDp - 300).dp,
        bottomSheetContent = {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Your bottom sheet content goes here
                Text("Bottom Sheet Content")
            }
        },
        sheetContent = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.starting_player),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(245.dp)
                        .padding(top = 16.dp)
                        .onGloballyPositioned { coordinates ->
                            bottomPosition.value =
                                coordinates.positionInWindow().y
                        }
                )
            }
        }
    )
}

@Preview(showSystemUi = true)
@Composable
fun BottomSheetPreview() {
    KhomasiTheme {
        MyScreen()
    }
}