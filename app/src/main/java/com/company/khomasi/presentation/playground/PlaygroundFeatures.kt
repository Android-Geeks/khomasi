package com.company.khomasi.presentation.playground

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.company.khomasi.R

@Composable
fun PlaygroundFeatures(
    featureList: List<String>
) {
    val myHeight = if (featureList.size > 3) 220.dp else 110.dp
    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(myHeight)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.field_features),
                style = MaterialTheme.typography.titleLarge
            )
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 100.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(featureList.take(6)) { feature ->
                    Card(
                        modifier = Modifier.height(50.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                        shape = MaterialTheme.shapes.medium,
                        colors = CardDefaults.cardColors(Color.Transparent)
                    ) {
                        Text(
                            text = feature,
                            style = MaterialTheme.typography.displayMedium,
                            color = MaterialTheme.colorScheme.tertiary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 3.dp, horizontal = 10.dp)
                        )
                    }
                }
            }

        }
    }
}