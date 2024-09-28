package com.company.rentafield.presentation.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.rentafield.presentation.components.connectionStates.ThreeBounce
import com.company.rentafield.presentation.screens.home.components.HomeContent
import com.company.rentafield.presentation.screens.home.vm.HomeReducer
import com.company.rentafield.presentation.screens.home.vm.HomeViewModel
import com.company.rentafield.utils.rememberFlowWithLifecycle


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigate: (HomeReducer.Effect) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val effect = rememberFlowWithLifecycle(viewModel.effect)


    LaunchedEffect(effect) {
        effect.collect { action ->
            onNavigate(action)
        }
    }

    HomeContent(
        firstName = state.localUser.firstName ?: "",
        profileImage = state.profileImage,
        playgroundsData = state.playgrounds,
        adsList = state.adList,
        canUploadVideo = state.canUploadVideo,
        userId = state.localUser.userID ?: "",
        sendEvent = viewModel::sendEventForEffect
    )
    if (state.isLoading) {
        ThreeBounce(
            color = MaterialTheme.colorScheme.primary,
            size = DpSize(75.dp, 75.dp),
            modifier = Modifier.fillMaxSize()
        )
    }
}
