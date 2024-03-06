package com.company.khomasi.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.company.khomasi.navigation.Screens
import com.company.khomasi.presentation.login.LoginScreen
import com.company.khomasi.presentation.login.LoginViewModel
import com.company.khomasi.presentation.loginOrSignup.LoginOrRegisterScreen
import com.company.khomasi.presentation.otpScreen.OtpScreen
import com.company.khomasi.presentation.register.RegisterScreen
import com.company.khomasi.presentation.resetPassword.ResetPassword

@Composable
fun AuthNavigator() {
    val navController = rememberNavController()
    Scaffold {
        NavHost(
            navController = navController,
            startDestination = Screens.LoginOrRegister.name,
            modifier = Modifier.padding(it)
        ) {
            composable(route = Screens.LoginOrRegister.name) {
                LoginOrRegisterScreen(
                    onLoginClick = { navController.navigate(Screens.Login.name) },
                    onRegisterClick = { navController.navigate(Screens.Register.name) }
                )
            }
            composable(route = Screens.Login.name) {
                val loginViewModel: LoginViewModel = hiltViewModel()
                LoginScreen(
                    onRegisterClick = { navController.navigate(Screens.Register.name) },
                    onForgotPasswordClick = { navController.navigate(Screens.ResetPassword.name) },
                    uiState = loginViewModel.uiState,
                    loginState = loginViewModel.loginState,
                    updatePassword = loginViewModel::updatePassword,
                    updateEmail = loginViewModel::updateEmail,
                    login = loginViewModel::login,
                    onLoginSuccess = loginViewModel::onLoginSuccess,
                    loginWithGmail = loginViewModel::loginWithGmail,
                    privacyAndPolicy = loginViewModel::privacyAndPolicy,
                    helpAndSupport = loginViewModel::helpAndSupport,
                    isValidEmailAndPassword = loginViewModel::isValidEmailAndPassword
                )
            }
            composable(route = Screens.Register.name) {
                RegisterScreen(
                    onLoginClick = { navController.navigate(Screens.Login.name) },
                    onDoneClick = { navController.navigate(Screens.OTP.name) },
                    backToLoginOrRegister = { navController.navigate(Screens.LoginOrRegister.name) }
                )
            }
            composable(route = Screens.OTP.name) {
                OtpScreen(
                    onEmailConfirmed = { navController.navigate(Screens.Login.name) }
                )
            }
            composable(route = Screens.ResetPassword.name) {

            ResetPassword(
                    resetViewModel = hiltViewModel(),
                    onCancelClick = { navController.popBackStack() },
                    onBackToLogin = { navController.navigate(Screens.Login.name) }
                )

            }
        }

    }
}