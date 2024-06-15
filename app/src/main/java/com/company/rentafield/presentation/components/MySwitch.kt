package com.company.rentafield.presentation.components

import android.content.res.Configuration
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.company.rentafield.theme.KhomasiTheme

@Composable
fun MySwitch(
    roundUp: Boolean,
    onRoundUpChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Switch(
        modifier = modifier,
        checked = roundUp,
        onCheckedChange = onRoundUpChanged
    )
}

@Preview(name = "Light Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SwitchPreview() {
    KhomasiTheme {
        MySwitch(roundUp = false, onRoundUpChanged = { })
    }
}