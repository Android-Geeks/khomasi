package com.company.rentafield.presentation.screens.playground.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.rentafield.R
import com.company.rentafield.theme.RentafieldTheme
import java.util.Locale

@Composable
fun SlotItem(
    slotStart: String,
    slotEnd: String,
    isSelected: Boolean,
    onClickSlot: () -> Unit = {}
) {
    val cardColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
    val textColor =
        if (isSelected) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.primary
    val textSize =
        if (isSelected) MaterialTheme.typography.titleLarge else MaterialTheme.typography.titleMedium
    val currentLanguage = Locale.getDefault().language
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .border(
                color = MaterialTheme.colorScheme.primary,
                width = 1.dp,
                shape = MaterialTheme.shapes.medium
            )
            .clickable {
                onClickSlot()
            },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(cardColor),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = slotStart,
                color = textColor,
                style = textSize,

                )
            Spacer(modifier = Modifier.width(4.dp))
            if (isSelected) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_left),
                    tint = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .size(24.dp)
                        .then(
                            if (currentLanguage == "en") {
                                Modifier.rotate(180f)
                            } else {
                                Modifier
                            }
                        ),
                    contentDescription = null,
                )
            } else {
                Text(
                    text = "_",
                    color = textColor,
                    style = textSize,
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = slotEnd,
                color = textColor,
                style = textSize,
            )
        }
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
fun SlotItemPreview() {
    RentafieldTheme {
        SlotItem(
            slotStart = "10:00",
            slotEnd = "11:00",
            isSelected = false,
        )
    }
}