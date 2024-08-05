package com.company.rentafield.presentation.screens.ai

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.company.rentafield.R
import com.company.rentafield.presentation.components.MyButton
import com.company.rentafield.presentation.screens.ai.components.ExoPlayerView

@Composable
fun AiVideoUpload(
    modifier: Modifier = Modifier,
    videoUri: Uri,
    onUpload: () -> Unit,
    onCancel: () -> Unit
) {
    ExoPlayerView(
        videoUri = videoUri,
        modifier = modifier
    )
    Spacer(modifier = Modifier.height(16.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MyButton(
            text = R.string.cancel,
            color = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            ),
            onClick = onCancel,
            modifier = Modifier.weight(1f)
        )
        MyButton(
            text = R.string.upload,
            onClick = onUpload,
            modifier = Modifier.weight(1f)
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
}