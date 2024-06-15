package com.company.rentafield.presentation.navigation.navigators

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.company.rentafield.navigation.Screens
import com.company.rentafield.presentation.onboarding.OnBoardingScreen
import com.company.rentafield.presentation.onboarding.OnboardingViewModel

fun NavGraphBuilder.onboardingNavigator() {
    navigation(
        startDestination = Screens.AppStartNavigation.OnBoarding.route,
        route = Screens.AppStartNavigation.route
    ) {
        composable(route = Screens.AppStartNavigation.OnBoarding.route) {
            val viewModel: OnboardingViewModel = hiltViewModel()
            OnBoardingScreen(
                onSkipClick = {
                    viewModel.onSkipClick()
                },
            )
        }
    }
}