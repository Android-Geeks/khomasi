package com.company.rentafield.presentation.onboarding

import androidx.annotation.StringRes
import com.company.rentafield.R

data class Page(
    @StringRes val title: Int,
    @StringRes val description: Int,
)

val pages = listOf(
    Page(
        title = R.string.explore,
        description = R.string.discover_a_wide_range_of_sports_fields,
    ),
    Page(
        title = R.string.book,
        description = R.string.book_a_time_that_suits_you,
    ),
)