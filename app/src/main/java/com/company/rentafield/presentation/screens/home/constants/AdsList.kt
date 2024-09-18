package com.company.rentafield.presentation.screens.home.constants

import androidx.compose.runtime.Stable
import com.company.rentafield.R
import com.company.rentafield.presentation.screens.home.model.AdsContent

@Stable
val adsList = listOf(
    AdsContent(
        imageSlider = R.drawable.ads_ai,
        contentText = R.string.ad_content_1,
    ),
    AdsContent(
        imageSlider = R.drawable.playground,
        contentText = R.string.ad_content_2,
    ),
    AdsContent(
        imageSlider = R.drawable.playground_image,
        contentText = R.string.ad_content_3,
    ),
    AdsContent(
        imageSlider = R.drawable.playground,
        contentText = R.string.ad_content_4,
    ),
)