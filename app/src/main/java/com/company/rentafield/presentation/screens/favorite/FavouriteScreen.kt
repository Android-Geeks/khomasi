package com.company.rentafield.presentation.screens.favorite

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.rentafield.presentation.theme.RentafieldTheme
import com.company.rentafield.utils.ThemePreviews
import com.company.rentafield.utils.rememberFlowWithLifecycle

@Composable
fun FavouriteScreen(
    viewModel: FavouriteViewModel = hiltViewModel(),
    onNavigate: (FavouriteReducer.Effect) -> Unit
) {
    val favUiState = viewModel.state.collectAsStateWithLifecycle().value
    val effect = rememberFlowWithLifecycle(viewModel.effect)
    val context = LocalContext.current


    LaunchedEffect(effect) {
        effect.collect { action ->
            when (action) {
                is FavouriteReducer.Effect.Error -> {
                    Toast.makeText(context, context.getString(action.message), Toast.LENGTH_LONG)
                        .show()
                }

                else -> onNavigate(action)
            }
        }
    }

    LaunchedEffect(Unit) { viewModel.getFavoritePlaygrounds() }

    FavouriteContent(
        uiState = favUiState,
        sendEvent = viewModel::sendEventForEffect
    )
}


@ThemePreviews
@Composable
fun FavouritePagePreview() {
    RentafieldTheme {
        FavouriteContent(uiState = FavouriteReducer.initial(), sendEvent = {})
    }
}

