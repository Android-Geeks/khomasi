package com.company.khomasi.presentation.booking

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
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.company.khomasi.R
import java.util.Locale

@Composable
fun SlotItem(
    slotStart: String,
    slotEnd: String,
    isSelected: MutableState<Boolean>,
    onClickSlot: () -> Unit = {}
) {
    val cardColor = if (isSelected.value) MaterialTheme.colorScheme.primary else Color.Transparent
    val textColor =
        if (isSelected.value) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.primary
    val textSize =
        if (isSelected.value) MaterialTheme.typography.titleLarge else MaterialTheme.typography.titleMedium
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
                isSelected.value = !isSelected.value
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
            if (isSelected.value) {
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