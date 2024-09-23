package com.company.rentafield.presentation.screens.myBookings.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.rentafield.R
import com.company.rentafield.presentation.components.MyButton
import com.company.rentafield.presentation.theme.RentafieldTheme

@Composable
fun EmptyScreen(
    onClickBookField: () -> Unit,
    modifier: Modifier = Modifier,
    isDark: Boolean = isSystemInDarkTheme()
) {
    Column(
        modifier = modifier
            .padding(start = 24.dp, end = 24.dp, top = 130.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = if (isDark) painterResource(id = R.drawable.dark_empty_booking_field) else painterResource(
                id = R.drawable.empty_booking_field__light
            ),
            contentDescription = "",
            alignment = Alignment.Center
        )
        Text(
            text = stringResource(R.string.no_bookings_yet),
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.height(108.dp))
        MyButton(
            text = R.string.book_field,
            onClick = onClickBookField,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun EmptyScreenPreview() {
    RentafieldTheme {
        EmptyScreen(onClickBookField = {})
    }
}