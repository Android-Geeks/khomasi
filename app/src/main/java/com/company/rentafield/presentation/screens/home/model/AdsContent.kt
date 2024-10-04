package com.company.rentafield.presentation.screens.home.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import com.company.rentafield.R

data class AdsContent(
    @DrawableRes val imageSlider: Int,
    @StringRes val contentText: Int,
)


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
