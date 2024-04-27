package com.company.khomasi.presentation.navigation.navigators

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.company.khomasi.navigation.Screens
import com.company.khomasi.presentation.onboarding.OnBoardingScreen
import com.company.khomasi.presentation.onboarding.OnboardingViewModel

fun NavGraphBuilder.onboardingNavigator(navController: NavController) {
    navigation(
        startDestination = Screens.AppStartNavigation.OnBoarding.route,
        route = Screens.AppStartNavigation.route
    ) {
        composable(route = Screens.AppStartNavigation.OnBoarding.route) {
            val viewModel: OnboardingViewModel = hiltViewModel()
            OnBoardingScreen(
                onSkipClick = {
                    navController.navigate(Screens.AuthNavigation.route)
                    viewModel.onSkipClick()
                },
            )
        }
    }
}