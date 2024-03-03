package com.company.khomasi.presentation.mainActivity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.company.khomasi.presentation.navigation.NavGraph
import com.company.khomasi.presentation.register.RegisterScreen
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
                    Log.d("TAG", mainViewModel.startDestination.value)
                    NavGraph(mainViewModel.startDestination.value)
                }
            }
        }
    }
}
