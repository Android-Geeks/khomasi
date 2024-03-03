package com.company.khomasi.presentation.navigation

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.company.khomasi.navigation.Routes
import com.company.khomasi.navigation.Screens
import com.company.khomasi.presentation.onboarding.OnBoardingScreen
import com.company.khomasi.presentation.onboarding.OnboardingViewModel
import com.company.khomasi.presentation.register.RegisterScreen
import com.company.khomasi.presentation.ui.screens.login.LoginScreen
import com.company.khomasi.presentation.ui.screens.loginOrSignup.LoginOrRegisterScreen
import com.company.khomasi.presentation.ui.screens.otpScreen.OtpScreen
import com.company.khomasi.theme.KhomasiTheme

@Composable
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        // Navigation for onboarding
        navigation(
            startDestination = Screens.OnBoarding.name,
            route = Routes.AppStartNavigation.name
        ) {
            composable(route = Screens.OnBoarding.name) {
                val viewModel: OnboardingViewModel = hiltViewModel()
                OnBoardingScreen(
                    onSkipClick = { viewModel.onSkipClick() },
                    // x = viewModel.x.collectAsState().value
                )
            }
        }

        // Navigation for Auth
        navigation(
            route = Routes.AuthNavigation.name,
            startDestination = Screens.Login.name
        ) {
            composable(route = Screens.LoginOrRegister.name) {
                LoginOrRegisterScreen()
            }
            composable(route = Screens.Login.name) {
                LoginScreen()
            }
            composable(route = Screens.Register.name) {
                RegisterScreen()
            }
            composable(route = Screens.OTP.name) {
                OtpScreen()
            }
            composable(route = Screens.ResetPassword.name) {
                // ResetPasswordScreen()
            }

        }

        // Navigation for Khomasi app
        navigation(
            route = Routes.KhomasiNavigation.name,
            startDestination = Screens.KhomasiNavigatorScreen.name
        ) {
            composable(route = Screens.KhomasiNavigatorScreen.name) {
                KhomasiNavigator()
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    KhomasiTheme {
        NavGraph(
            startDestination = Routes.KhomasiNavigation.name
        )
    }
}