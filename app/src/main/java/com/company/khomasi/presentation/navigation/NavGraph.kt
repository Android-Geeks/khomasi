package com.company.khomasi.presentation.navigation

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.company.khomasi.navigation.Screens
import com.company.khomasi.navigation.listOfNavItems
import com.company.khomasi.presentation.components.connectionStates.LossConnection
import com.company.khomasi.presentation.navigation.components.BottomNavigationBar
import com.company.khomasi.presentation.navigation.navigators.authNavigator
import com.company.khomasi.presentation.navigation.navigators.khomasiNavigator
import com.company.khomasi.presentation.navigation.navigators.onboardingNavigator
import com.company.khomasi.theme.KhomasiTheme
import com.company.khomasi.utils.ConnectivityObserver

@Composable
fun NavGraph(
    startDestination: String,
    isNetworkAvailable: ConnectivityObserver.Status
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    if (isNetworkAvailable == ConnectivityObserver.Status.Unavailable ||
        isNetworkAvailable == ConnectivityObserver.Status.Lost
    ) {
        LossConnection()
        return
    }

    Scaffold(
        bottomBar =
        {
            if (navBackStackEntry?.destination?.route in listOfNavItems.map { it.route }) {
                BottomNavigationBar(
                    navController = navController,
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(paddingValues)
        ) {
            // Navigation for onboarding
            onboardingNavigator()

            // Navigation for Auth
            authNavigator(navController)

            // Navigation for Khomasi app
            khomasiNavigator(navController)
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    KhomasiTheme {
        NavGraph(
            startDestination = Screens.KhomasiNavigation.route,
            isNetworkAvailable = ConnectivityObserver.Status.Unavailable
        )
    }
}