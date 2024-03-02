package com.company.khomasi.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import com.nulabinc.zxcvbn.Zxcvbn
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasi.theme.KhomasiTheme

@Composable
fun PasswordStrengthMeter(
    password: String,
    enable: Boolean         // Change happened
    // "to avoid coloring the first partition when passStrength is 0 "
) {
    val passwordStrength = Zxcvbn().measure(password).score

    val indicatorColoringRange by remember {
        mutableStateOf(listOf(
            listOf(0, 1, 2, 3, 4),
            listOf(2, 3, 4),
            listOf(3, 4),
            listOf(4)
        ))
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Center
    ){
        indicatorColoringRange.forEach { config ->
            PasswordStrengthIndicator(
                passwordStrength = passwordStrength,
                coloringEnableRange = config,
                modifier = Modifier.weight(1f),
                enable = enable
            )
        }
    }
}

@Composable
fun PasswordStrengthIndicator(
    passwordStrength: Int,
    coloringEnableRange: List<Int>,
    modifier : Modifier = Modifier,
    enable : Boolean = false,
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(4.dp)
            .padding(horizontal = 4.dp)
            .background(
                color = Color(0xFFDDDDDD),
                shape = RoundedCornerShape(100.dp)
            )
    ){
        if (passwordStrength in coloringEnableRange && enable){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = when (passwordStrength) {
                            0, 1 -> Color.Red
                            2, 3 -> Color(0xFFF3F311)
                            4 -> Color.Green
                            else -> Color(0xFFDDDDDD)
                        }
                    )
            )
        }
    }
}

@Preview
@Composable
fun PasswordStrengthMeterPreview() {
    KhomasiTheme{ PasswordStrengthMeter("", false) }
}