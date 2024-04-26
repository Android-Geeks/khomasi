package com.company.khomasi.presentation.navigation

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.company.khomasi.navigation.Screens
import com.company.khomasi.navigation.listOfNavItems
import com.company.khomasi.presentation.navigation.components.BottomNavigationBar
import com.company.khomasi.presentation.onboarding.OnBoardingScreen
import com.company.khomasi.presentation.onboarding.OnboardingViewModel
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
            navigation(
                startDestination = Screens.AppStartNavigation.OnBoarding.route,
                route = Screens.AppStartNavigation.route
            ) {
                composable(route = Screens.AppStartNavigation.OnBoarding.route) {
                    val viewModel: OnboardingViewModel = hiltViewModel()
                    OnBoardingScreen(
                        onSkipClick = viewModel::onSkipClick,
                    )
                }
            }
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