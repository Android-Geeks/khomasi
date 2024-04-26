package com.company.khomasi.presentation.navigation

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.company.khomasi.navigation.Screens
import com.company.khomasi.presentation.login.LoginScreen
import com.company.khomasi.presentation.login.LoginViewModel
import com.company.khomasi.presentation.loginOrSignup.LoginOrRegisterScreen
import com.company.khomasi.presentation.navigation.components.sharedViewModel
import com.company.khomasi.presentation.otpScreen.OtpScreen
import com.company.khomasi.presentation.otpScreen.OtpViewModel
import com.company.khomasi.presentation.register.RegisterScreen
import com.company.khomasi.presentation.register.RegisterViewModel
import com.company.khomasi.presentation.resetPassword.ResetPassword
import com.company.khomasi.presentation.resetPassword.ResetPasswordViewModel


fun NavGraphBuilder.authNavigator(navController: NavController) {
    navigation(
        route = Screens.AuthNavigation.route,
        startDestination = Screens.AuthNavigation.LoginOrRegister.route
    ) {
        composable(route = Screens.AuthNavigation.LoginOrRegister.route) {
            LoginOrRegisterScreen(
                onLoginClick = { navController.navigate(Screens.AuthNavigation.Login.route) },
                onRegisterClick = { navController.navigate(Screens.AuthNavigation.Register.route) }
            )
        }

        composable(route = Screens.AuthNavigation.Login.route) {
            val loginViewModel: LoginViewModel = hiltViewModel()
            LoginScreen(
                onRegisterClick = { navController.navigate(Screens.AuthNavigation.Register.route) },
                onForgotPasswordClick = { navController.navigate(Screens.AuthNavigation.ResetPassword.route) },
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

        register(navController = navController)

        composable(route = Screens.AuthNavigation.OTP.route) {
            val otpViewModel: OtpViewModel = hiltViewModel()
            OtpScreen(
                onEmailConfirmed = { navController.navigate(Screens.AuthNavigation.Login.route) },
                uiState = otpViewModel.uiState.collectAsState(),
                confirmEmailState = otpViewModel.confirmEmailState,
                otpState = otpViewModel.otpState,
                getRegisterOtp = otpViewModel::getRegisterOtp,
                updateSmsCode = otpViewModel::updateSmsCode,
                resendCode = otpViewModel::resendCode,
                confirmEmail = otpViewModel::confirmEmail,
                startTimer = otpViewModel::startTimer,
                resetTimer = otpViewModel::resetTimer,
            )
        }

        resetPassword(navController = navController)

    }
}


fun NavGraphBuilder.register(navController: NavController) {
    navigation(
        route = Screens.AuthNavigation.Register.route,
        startDestination = Screens.AuthNavigation.Register.NameAndPhone.route
    ) {
        composable(route = Screens.AuthNavigation.Register.NameAndPhone.route) {
            val registerViewModel = it.sharedViewModel<RegisterViewModel>(navController)
            RegisterScreen(
                onLoginClick = { navController.navigate(Screens.AuthNavigation.Login.route) },
                onDoneClick = { navController.navigate(Screens.AuthNavigation.OTP.route) },
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
        composable(route = Screens.AuthNavigation.Register.EmailAndPassword.route) {
            val registerViewModel = it.sharedViewModel<RegisterViewModel>(navController)

        }

    }
}


fun NavGraphBuilder.resetPassword(navController: NavController) {
    navigation(
        route = Screens.AuthNavigation.ResetPassword.route,
        startDestination = Screens.AuthNavigation.ResetPassword.Email.route
    ) {
        composable(route = Screens.AuthNavigation.ResetPassword.Email.route) {
            val resetPasswordViewModel = it.sharedViewModel<ResetPasswordViewModel>(navController)
            ResetPassword(
                onCancelClick = { navController.popBackStack() },
                onBackToLogin = { navController.navigate(Screens.AuthNavigation.Login.route) },
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
        composable(route = Screens.AuthNavigation.ResetPassword.Confirmation.route) {
            val resetPasswordViewModel = it.sharedViewModel<ResetPasswordViewModel>(navController)

        }
    }
}