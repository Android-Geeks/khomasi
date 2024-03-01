package com.company.khomasi.presentation.onboarding

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.MyTextButton
import com.company.khomasi.presentation.onboarding.components.OnBoardingPage
import com.company.khomasi.presentation.onboarding.components.PagerIndicator
import com.company.khomasi.theme.KhomasiTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    onSkipClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize().systemBarsPadding()) {
        val pagerState = rememberPagerState(initialPage = 0) {
            pages.size
        }
        val scope = rememberCoroutineScope()

        MyTextButton(
            text = R.string.skip,
            onClick = onSkipClick,
            isUnderlined = false,
            modifier = Modifier
                .align(Alignment.End)
        )
        Spacer(modifier = Modifier.weight(1f))
        HorizontalPager(state = pagerState) { index ->
            OnBoardingPage(
                page = pages[index]
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PagerIndicator(
                pagesSize = pages.size,
                selectedPage = pagerState.currentPage
            )
            Spacer(modifier = Modifier.height(16.dp))
            MyButton(
                text = R.string.next,
                onClick = {
                    scope.launch {
                        if (pagerState.currentPage < pages.size - 1) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        } else {
                            onSkipClick()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                contentPadding = PaddingValues(vertical = 9.dp),


                )
        }

    }
}

@Preview(name = "en", uiMode = UI_MODE_NIGHT_NO, showSystemUi = true)
@Preview(name = "ar", uiMode = UI_MODE_NIGHT_NO, showSystemUi = true, locale = "ar")
@Composable
fun OnBoardingScreenPreview() {
    KhomasiTheme {
        OnBoardingScreen(
            onSkipClick = {}
        )
    }
}