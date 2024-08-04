package com.company.rentafield.presentation.screens.venues.component

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.rentafield.R
import com.company.rentafield.theme.RentafieldTheme

@SuppressLint("UnrememberedMutableState")
@Composable
fun TypeCard(
    onClickCard: () -> Unit,
    type: Int,
    isSelected: MutableState<Boolean>,
) {
    val cardColor = if (isSelected.value) MaterialTheme.colorScheme.primary else Color.Transparent
    val textColor =
        if (isSelected.value) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.primary


    Card(
        modifier = Modifier
            .clickable {
                onClickCard()
                isSelected.value = !isSelected.value
            }
            .height(50.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(cardColor)
    ) {
        Text(
            text = "$type ${stringResource(id = R.string.field_size_mul)} $type",
            style = MaterialTheme.typography.displayMedium,
            color = textColor,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 22.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PlaygroundTypePreview() {
    RentafieldTheme {
        TypeCard(onClickCard = {},
            type = 5,
            isSelected = remember { mutableStateOf(false) })
    }
}