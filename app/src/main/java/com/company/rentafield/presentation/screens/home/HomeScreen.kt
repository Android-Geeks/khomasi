package com.company.rentafield.presentation.screens.home

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.rentafield.utils.rememberFlowWithLifecycle


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigate: (HomeReducer.Effect) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val effect = rememberFlowWithLifecycle(viewModel.effect)
    val context = LocalContext.current


    LaunchedEffect(effect) {
        effect.collect { action ->
            when (action) {
                is HomeReducer.Effect.Error -> {
                    Toast.makeText(context, context.getString(action.message), Toast.LENGTH_LONG).show()
                }

                else -> onNavigate(action)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getHomeData()
    }

    HomeContent(
        state = state,
        sendEvent = viewModel::sendEventForEffect
    )
}
