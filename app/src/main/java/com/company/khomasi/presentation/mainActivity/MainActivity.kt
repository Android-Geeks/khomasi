package com.company.khomasi.presentation.mainActivity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.khomasi.presentation.navigation.NavGraph
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.utils.ConnectivityObserver
import com.company.khomasi.utils.NetworkConnectivityObserver
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
            val isNetworkAvailable by connectivityObserver.observe().collectAsStateWithLifecycle(
                initialValue = ConnectivityObserver.Status.Unavailable
            )
            KhomasiTheme {
                Log.d("ActivityMain", mainViewModel.startDestination.value)
                NavGraph(
                    startDestination = startDestination,
                    isNetworkAvailable = isNetworkAvailable
                )
            }
        }
    }
}
