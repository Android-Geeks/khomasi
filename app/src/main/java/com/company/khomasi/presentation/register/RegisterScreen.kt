package com.company.khomasi.presentation.register

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.company.khomasi.R
import com.company.khomasi.presentation.components.MyBottomSheetScaffold
import com.company.khomasi.presentation.register.pages.RegisterDataPage
import com.company.khomasi.theme.KhomasiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    MyBottomSheetScaffold(
        scaffoldState = scaffoldState,
        bottomSheetContent = {
            RegisterDataPage(viewModel = viewModel)
        },
        sheetContent = {
            Image(
                painter = painterResource(id = R.drawable.starting_player),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(245.dp)
                    .padding(it)
            )
        },
        modifier = Modifier
    )
}


@Preview(name = "Night", showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Preview(name = "Light", showSystemUi = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun BottomSheetPreview() {
    KhomasiTheme {
        RegisterScreen(
            viewModel = RegisterViewModel()
        )
    }
}