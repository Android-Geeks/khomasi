package com.company.khomasi.presentation.mainActivity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.company.khomasi.presentation.navigation.NavGraph
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
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            KhomasiTheme {
                Surface {
                    Log.d("ActivityMain", mainViewModel.startDestination.value)
                    NavGraph(mainViewModel.startDestination.value)
                }
            }
        }
    }
}
