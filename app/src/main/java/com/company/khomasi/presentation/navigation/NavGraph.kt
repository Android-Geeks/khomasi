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
import com.company.khomasi.presentation.navigation.components.BottomNavigationBar
import com.company.khomasi.presentation.navigation.navigators.authNavigator
import com.company.khomasi.presentation.navigation.navigators.khomasiNavigator
import com.company.khomasi.presentation.navigation.navigators.onboardingNavigator
import com.company.khomasi.theme.KhomasiTheme

@Composable
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        bottomBar =
        {
            BottomNavigationBar(
                navController = navController,
                bottomBarState = navBackStackEntry?.destination?.route
                        in listOfNavItems.map { it.route }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(paddingValues)
        ) {
            // Navigation for onboarding
            onboardingNavigator(navController)

            // Navigation for Auth
            authNavigator(navController = navController)

            // Navigation for Khomasi app
            khomasiNavigator(navController = navController)
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    KhomasiTheme {
        NavGraph(
            startDestination = Screens.KhomasiNavigation.route
        )
    }
}