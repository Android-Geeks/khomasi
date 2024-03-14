package com.company.khomasi.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.company.khomasi.presentation.otpScreen.OtpViewModel
import com.company.khomasi.presentation.register.RegisterScreen
import com.company.khomasi.presentation.register.RegisterViewModel
import com.company.khomasi.presentation.resetPassword.ResetPassword
import com.company.khomasi.presentation.resetPassword.ResetPasswordViewModel

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
                    loginWithGmail = loginViewModel::loginWithGmail,
                    privacyAndPolicy = loginViewModel::privacyAndPolicy,
                    helpAndSupport = loginViewModel::helpAndSupport,
                    isValidEmailAndPassword = loginViewModel::isValidEmailAndPassword
                )
            }
            composable(route = Screens.Register.name) {
                val registerViewModel: RegisterViewModel = hiltViewModel()
                RegisterScreen(
                    onLoginClick = { navController.navigate(Screens.Login.name) },
                    onDoneClick = { navController.navigate(Screens.OTP.name) },
                    onBackFromStack = { navController.popBackStack() },
                    onRegister = registerViewModel::onRegister,
                    uiState = registerViewModel.uiState.collectAsState(),
                    registerState = registerViewModel.registerState,
                    onFirstNameChange = registerViewModel::onFirstNameChange,
                    isValidNameAndPhoneNumber = registerViewModel::isValidNameAndPhoneNumber,
                    isValidEmailAndPassword = registerViewModel::isValidEmailAndPassword,
                    onNextClick = registerViewModel::onNextClick,
                    onLastNameChange = registerViewModel::onLastNameChange,
                    onEmailChange = registerViewModel::onEmailChange,
                    onPasswordChange = registerViewModel::onPasswordChange,
                    onConfirmPasswordChange = registerViewModel::onConfirmPasswordChange,
                    onPhoneNumberChange = registerViewModel::onPhoneNumberChange,
                    updateLocation = registerViewModel::updateLocation,
                    onBack = registerViewModel::onBack
                )
            }
            composable(route = Screens.OTP.name) {
                val otpViewModel: OtpViewModel = hiltViewModel()
                OtpScreen(
                    onEmailConfirmed = { navController.navigate(Screens.Login.name) },
                    uiState = otpViewModel.uiState.collectAsState(),
                    confirmEmailState = otpViewModel.confirmEmailState,
                    otpState = otpViewModel.otpState,
                    updateSmsCode = otpViewModel::updateSmsCode,
                    resendCode = otpViewModel::resendCode,
                    confirmEmail = otpViewModel::confirmEmail,
                    startTimer = otpViewModel::startTimer,
                    resetTimer = otpViewModel::resetTimer,
                )
            }
            composable(route = Screens.ResetPassword.name) {
                val resetPasswordViewModel: ResetPasswordViewModel = hiltViewModel()
            ResetPassword(
                onCancelClick = { navController.popBackStack() },
                onBackToLogin = { navController.navigate(Screens.Login.name) },
                uiState = resetPasswordViewModel.resetUiState.collectAsState(),
                verificationRes = resetPasswordViewModel.verificationRes,
                recoverResponse = resetPasswordViewModel.recoverResponse,
                onUserEmailChange = resetPasswordViewModel::onUserEmailChange,
                onClickButtonScreen1 = resetPasswordViewModel::onClickButtonScreen1,
                onEnteringVerificationCode = resetPasswordViewModel::onEnteringVerificationCode,
                verifyVerificationCode = resetPasswordViewModel::verifyVerificationCode,
                onEnteringPassword = resetPasswordViewModel::onEnteringPassword,
                onReTypingPassword = resetPasswordViewModel::onReTypingPassword,
                onButtonClickedScreen2 = resetPasswordViewModel::onButtonClickedScreen2,
                onBack = resetPasswordViewModel::onBack,
                onNextClick = resetPasswordViewModel::onNextClick,
                )

            }
        }

    }
}