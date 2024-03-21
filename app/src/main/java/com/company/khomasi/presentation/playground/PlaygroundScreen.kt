package com.company.khomasi.presentation.playground

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.company.khomasi.domain.DataState
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.utils.convertToBitmap

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun PlaygroundScreen() {
    val playgroundViewModel: PlaygroundViewModel = hiltViewModel()
    val state = playgroundViewModel.playgroundState.collectAsState().value
    LaunchedEffect(key1 = playgroundViewModel.playgroundState.value) {
        Log.d("FavoriteScreen", "FavoriteScreen: ${playgroundViewModel.playgroundState.value}")
    }
    if (state is DataState.Success) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Text(text = state.data.playground.holidays)
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(state.data.playgroundPictures[0].picture.convertToBitmap())
                    .crossfade(true).build(), contentDescription = null
            )
        }
    }

}

@Preview
@Composable
fun kkk() {
    KhomasiTheme {
        PlaygroundScreen()
    }
}
