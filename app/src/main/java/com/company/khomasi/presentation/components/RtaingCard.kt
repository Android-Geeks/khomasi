package com.company.khomasi.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasi.theme.KhomasiTheme

@Composable
fun RatingCard(modifier: Modifier = Modifier){
    Card (modifier= modifier
        .fillMaxWidth()
        .height(111.dp)
        .background(color =MaterialTheme.colorScheme.background )
    ){
        Row {
            MyButton(text = "rating", onClick = { /*TODO*/ },
                modifier =  modifier
                    .height(32.dp)
                    .background(color = MaterialTheme.colorScheme.primary,
                        shape = MaterialTheme.shapes.small)
            )
            Column {
              Text(text = "")

            Text(text = "")
        }}
    }
}

@Preview(name = "dark", uiMode = UI_MODE_NIGHT_YES)
@Preview(name = "light", uiMode = UI_MODE_NIGHT_NO)

@Composable
fun PreviewRatingCard(){
    KhomasiTheme {
        RatingCard()
    }

}