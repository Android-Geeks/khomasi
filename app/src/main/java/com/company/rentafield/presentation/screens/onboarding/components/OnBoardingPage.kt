package com.company.rentafield.presentation.screens.onboarding.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.rentafield.R
import com.company.rentafield.presentation.screens.onboarding.Page
import com.company.rentafield.presentation.screens.onboarding.pages
import com.company.rentafield.presentation.theme.RentafieldTheme


@Composable
fun OnBoardingPage(
    modifier: Modifier = Modifier,
    page: Page,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(
                id = getResourceId(
                    page.title,
                    isSystemInDarkTheme()
                )
            ),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = stringResource(id = page.title),
            style = MaterialTheme.typography.displayLarge.copy(
                fontSize = 28.sp
            ),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )
        Text(
            text = stringResource(id = page.description),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
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

@Preview(
    name = "DARK | EN",
    locale = "en",
    uiMode = UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF0E0E0E,
    showBackground = true
)
@Preview(
    name = "LIGHT | AR",
    locale = "ar",
    uiMode = UI_MODE_NIGHT_NO,
    backgroundColor = 0xFFF5F5F5,
    showBackground = true
)
@Composable
fun OnBoardingPagePreview() {
    RentafieldTheme {
        OnBoardingPage(
            page = pages[0]
        )
    }
}












