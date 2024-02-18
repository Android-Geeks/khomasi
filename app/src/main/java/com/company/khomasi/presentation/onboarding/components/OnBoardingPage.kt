package com.company.khomasi.presentation.onboarding.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.company.khomasi.R
import com.company.khomasi.presentation.components.MyTextButton
import com.company.khomasi.presentation.onboarding.Page
import com.company.khomasi.presentation.onboarding.pages
import com.company.khomasi.theme.KhomasiTheme


@Composable
fun OnBoardingPage(
    modifier: Modifier = Modifier,
    page: Page,
) {
    Column(modifier = modifier) {
        MyTextButton(
            text = R.string.skip,
            onClick = { /*TODO*/ },
            isUnderlined = false,
            modifier = Modifier
        )
        Image(
            painter = painterResource(
                id = when (page.title) {
                    R.string.explore ->
                        when(isSystemInDarkTheme()){
                            false -> R.drawable.light_onboarding1
                            true -> R.drawable.dark_onboarding1
                        }
                    R.string.book ->
                        when(isSystemInDarkTheme()){
                            false -> R.drawable.light_onboarding2
                            true -> R.drawable.dark_onboarding2
                        }
                    else -> 0
                }
            ),
            contentDescription = null
        )
    }
}

fun getResourceId(pageTitle: Int, darkTheme: Boolean): Int {
    return when (pageTitle) {
        R.string.explore -> if (darkTheme) R.drawable.dark_onboarding1 else R.drawable.light_onboarding1
        R.string.book -> if (darkTheme) R.drawable.dark_onboarding2 else R.drawable.light_onboarding2
        else -> 0
    }
}

@Preview(name = "Light", uiMode = UI_MODE_NIGHT_NO, showSystemUi = true)
@Composable
fun OnBoardingPagePreview() {
    KhomasiTheme {
        OnBoardingPage(
            page = pages[0]
        )
    }
}












