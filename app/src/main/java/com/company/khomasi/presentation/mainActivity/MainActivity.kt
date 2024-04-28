package com.company.khomasi.presentation.mainActivity

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material3.Surface
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.company.khomasi.presentation.playground.PlaygroundReviews
import com.company.khomasi.presentation.playground.PlaygroundViewModel
import com.company.khomasi.theme.KhomasiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()

    @RequiresApi(Build.VERSION_CODES.O)
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
//                    Log.d("TAG", mainViewModel.startDestination.value)
//                    NavGraph(mainViewModel.startDestination.value)
                    val vm: PlaygroundViewModel = hiltViewModel()
                    PlaygroundReviews(
                        getPlaygroundReviews = vm::getPlaygroundReviews,
                        reviewsState = vm.reviewsState
                    )
                }
            }
        }
    }
}
