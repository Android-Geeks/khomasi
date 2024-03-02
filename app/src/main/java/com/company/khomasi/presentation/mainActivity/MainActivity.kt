package com.company.khomasi.presentation.mainActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.company.khomasi.presentation.ui.screens.recreateNewPassword.RecreatePassScreen1
import com.company.khomasi.presentation.ui.screens.recreateNewPassword.RecreatePassScreen2
import com.company.khomasi.theme.KhomasiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen().apply {
            setKeepOnScreenCondition(condition = { mainViewModel.splashCondition.value })
        }
        super.onCreate(savedInstanceState)
        setContent {
            KhomasiTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Log.d("TAG", mainViewModel.startDestination.value)
//                    NavGraph(mainViewModel.startDestination.value)
                    RecreatePassScreen2()
                }
            }
        }
    }
}
