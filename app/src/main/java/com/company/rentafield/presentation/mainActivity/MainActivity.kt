package com.company.rentafield.presentation.mainActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.rentafield.presentation.navigation.NavGraph
import com.company.rentafield.theme.KhomasiTheme
import com.company.rentafield.utils.ConnectivityObserver
import com.company.rentafield.utils.NetworkConnectivityObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var connectivityObserver: ConnectivityObserver

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityObserver = NetworkConnectivityObserver(applicationContext)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen().apply {
            setKeepOnScreenCondition(condition = { mainViewModel.splashCondition.value })
        }
        enableEdgeToEdge()
        setContent {
            val startDestination by mainViewModel.startDestination.collectAsStateWithLifecycle()
            val isNetworkAvailable by connectivityObserver.observe()
                .collectAsStateWithLifecycle(
                    initialValue = ConnectivityObserver.Status.Unavailable
                )
            KhomasiTheme {
                NavGraph(
                    startDestination = startDestination,
                    isNetworkAvailable = isNetworkAvailable
                )
            }
        }
        //hideSystemUI()
    }

//    private fun hideSystemUI() {
//        //Hides the ugly action bar at the top
//        actionBar?.hide()
//        //Hide the status bars
//        WindowCompat.setDecorFitsSystemWindows(window, false)
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
//            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        } else {
//            window.insetsController?.apply {
//                hide(WindowInsets.Type.statusBars())
//                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//            }
//        }
//    }
}


