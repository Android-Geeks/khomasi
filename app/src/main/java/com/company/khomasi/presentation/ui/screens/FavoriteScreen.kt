package com.company.khomasi.presentation.ui.screens


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

@Composable
fun FavoriteScreen(
    viewModel: VM = hiltViewModel()
) {
    val state = viewModel.registerState.collectAsState().value
    LaunchedEffect(key1 = viewModel.registerState.value) {
        Log.d("FavoriteScreen", "FavoriteScreen: ${viewModel.registerState.value}")
    }
    if (state is DataState.Success) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Text(text = state.data.playgrounds[0].name)
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(state.data.playgrounds[1].playgroundPicture?.convertToBitmap())
                    .crossfade(true)
                    .build(),
                contentDescription = null
            )
        }

    }
}


@Preview(showSystemUi = true)
@Composable
fun FavoritePreview() {

    KhomasiTheme {
        FavoriteScreen()
    }
}