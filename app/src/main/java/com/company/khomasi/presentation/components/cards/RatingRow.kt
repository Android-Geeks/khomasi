package com.company.khomasi.presentation.components.cards

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.RatingBar
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.company.khomasi.theme.KhomasiTheme


@Composable
fun RatingRow(
    rating: Float,
    onRatingChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    AndroidView(
        modifier = modifier
            .wrapContentWidth(Alignment.CenterHorizontally),

        factory = { context ->
            RatingBar(context).apply {
                stepSize = 1f
            }
        },
        update = { ratingBar ->
            ratingBar.rating = rating
            ratingBar.setOnRatingBarChangeListener { _, _, _ ->
                onRatingChange(ratingBar.rating)
            }
        },
    )
}

@Preview(name = "Light Mode", showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun LightRatingRowPreview() {
    KhomasiTheme {
        RatingRow(rating = 4f, onRatingChange = {})
    }

}