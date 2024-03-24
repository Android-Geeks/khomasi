package com.company.khomasi.presentation.profile

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.company.khomasi.theme.KhomasiTheme

@Composable
fun ProfileScreen() {

}


@Preview(name = "light", locale = "ar", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "dark", locale = "en", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SearchScreenPreview() {
    KhomasiTheme {
        ProfileScreen()
    }
}