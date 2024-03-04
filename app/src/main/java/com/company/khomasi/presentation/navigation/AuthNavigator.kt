package com.company.khomasi.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.company.khomasi.navigation.Screens
import com.company.khomasi.presentation.login.LoginScreen
import com.company.khomasi.presentation.loginOrSignup.LoginOrRegisterScreen
import com.company.khomasi.presentation.otpScreen.OtpScreen
import com.company.khomasi.presentation.register.RegisterScreen
import com.company.khomasi.presentation.resetPassword.ResetPassword1
import com.company.khomasi.presentation.resetPassword.ResetPassword2

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
                LoginScreen(
                    onRegisterClick = { navController.navigate(Screens.Register.name) },
                    onForgotPasswordClick = { navController.navigate(Screens.ResetPassword1.name) },
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
                OtpScreen()
            }
            composable(route = Screens.ResetPassword1.name) {
                ResetPassword1(
                    onSetPasswordClick = { navController.navigate(Screens.ResetPassword2.name) },
                    onCancelClick = { navController.popBackStack() }
                )
            }
            composable(route = Screens.ResetPassword2.name) {
                ResetPassword2(
                    onBackToLogin = { navController.navigate(Screens.Login.name) }
                )
            }
        }
    }

}