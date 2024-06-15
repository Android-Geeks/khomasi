package com.company.rentafield.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.rentafield.theme.KhomasiTheme
import com.company.rentafield.utils.CheckInputValidation
import com.nulabinc.zxcvbn.Zxcvbn

@Composable
fun PasswordStrengthMeter(
    password: String,
    enable: Boolean         // Change happened
    // "to avoid coloring the first partition when passStrength is 0 "
) {
    var passwordStrength by remember(password) {
        mutableIntStateOf(Zxcvbn().measure(password).score)
    }
    Log.d("PasswordStrengthMeter", "PasswordStrengthMeter: $password, $passwordStrength")

    if (!CheckInputValidation.isPasswordValid(password)) {
        if (passwordStrength > 2) {
            passwordStrength = 2
        }
    }


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
                color = MaterialTheme.colorScheme.background,
                shape = CircleShape
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
                            else -> MaterialTheme.colorScheme.background
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