package com.company.khomasi.presentation.navigation.navigators

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.company.khomasi.navigation.Screens
import com.company.khomasi.presentation.login.LoginScreen
import com.company.khomasi.presentation.login.LoginViewModel
import com.company.khomasi.presentation.loginOrSignup.LoginOrRegisterScreen
import com.company.khomasi.presentation.navigation.components.sharedViewModel
import com.company.khomasi.presentation.otpScreen.OtpScreen
import com.company.khomasi.presentation.otpScreen.OtpViewModel
import com.company.khomasi.presentation.register.RegisterEmailAndPassword
import com.company.khomasi.presentation.register.RegisterNameAndPhone
import com.company.khomasi.presentation.register.RegisterViewModel
import com.company.khomasi.presentation.resetPassword.EmailVerification
import com.company.khomasi.presentation.resetPassword.PasswordConfirmation
import com.company.khomasi.presentation.resetPassword.ResetPasswordViewModel


fun NavGraphBuilder.authNavigator(
    navController: NavHostController,
) {
    navigation(
        route = Screens.AuthNavigation.route,
        startDestination = Screens.AuthNavigation.LoginOrRegister.route
    ) {
        composable(route = Screens.AuthNavigation.LoginOrRegister.route) {
            LoginOrRegisterScreen(
                onLoginClick = {
                    navController.navigate(Screens.AuthNavigation.Login.route)
                },
                onRegisterClick = {
                    navController.navigate(Screens.AuthNavigation.Register.route)
                }
            )
        }

        composable(route = Screens.AuthNavigation.Login.route) {
            val loginViewModel: LoginViewModel = hiltViewModel()
            LoginScreen(
                uiState = loginViewModel.uiState,
                loginState = loginViewModel.loginState,
                updatePassword = loginViewModel::updatePassword,
                updateEmail = loginViewModel::updateEmail,
                login = loginViewModel::login,
                onLoginSuccess = loginViewModel::onLoginSuccess,
                onRegisterClick = { navController.navigate(Screens.AuthNavigation.Register.route) },
                onForgotPasswordClick = { navController.navigate(Screens.AuthNavigation.ResetPassword.route) },
                verifyEmail = {
                    loginViewModel.verifyEmail()
                    navController.navigate(Screens.AuthNavigation.OTP.route)
                },
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
                uiState = otpViewModel.uiState,
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


fun NavGraphBuilder.register(navController: NavHostController) {
    navigation(
        route = Screens.AuthNavigation.Register.route,
        startDestination = Screens.AuthNavigation.Register.NameAndPhone.route
    ) {
        composable(route = Screens.AuthNavigation.Register.NameAndPhone.route) {
            val registerViewModel = it.sharedViewModel<RegisterViewModel>(navController)
            RegisterNameAndPhone(
                uiState = registerViewModel.uiState,
                updateLocation = registerViewModel::updateLocation,
                onFirstNameChange = registerViewModel::onFirstNameChange,
                onLastNameChange = registerViewModel::onLastNameChange,
                onPhoneNumberChange = registerViewModel::onPhoneNumberChange,
                isValidNameAndPhoneNumber = registerViewModel::isValidNameAndPhoneNumber,
                onLoginClick = { navController.navigate(Screens.AuthNavigation.Login.route) },
                onNextClick = { navController.navigate(Screens.AuthNavigation.Register.EmailAndPassword.route) },
            )
        }
        composable(route = Screens.AuthNavigation.Register.EmailAndPassword.route) {
            val registerViewModel = it.sharedViewModel<RegisterViewModel>(navController)
            RegisterEmailAndPassword(
                uiState = registerViewModel.uiState,
                registerState = registerViewModel.registerState,
                onDoneClick = { navController.navigate(Screens.AuthNavigation.OTP.route) },
                onRegister = registerViewModel::onRegister,
                isValidEmailAndPassword = registerViewModel::isValidEmailAndPassword,
                onEmailChange = registerViewModel::onEmailChange,
                onPasswordChange = registerViewModel::onPasswordChange,
                onConfirmPasswordChange = registerViewModel::onConfirmPasswordChange,
                onLoginClick = { navController.navigate(Screens.AuthNavigation.Login.route) },
            )
        }
    }
}


fun NavGraphBuilder.resetPassword(navController: NavHostController) {
    navigation(
        route = Screens.AuthNavigation.ResetPassword.route,
        startDestination = Screens.AuthNavigation.ResetPassword.Email.route
    ) {
        composable(route = Screens.AuthNavigation.ResetPassword.Email.route) {
            val resetPasswordViewModel = it.sharedViewModel<ResetPasswordViewModel>(navController)
            EmailVerification(
                uiState = resetPasswordViewModel.resetUiState,
                verificationRes = resetPasswordViewModel.verificationRes,
                onCorrectCodeChange = resetPasswordViewModel::onCorrectCodeChange,
                onUserEmailChange = resetPasswordViewModel::onUserEmailChange,
                onCancelClick = navController::popBackStack,
                onClickButtonScreen1 = resetPasswordViewModel::onClickButtonScreen1,
                onSetPasswordClick = { navController.navigate(Screens.AuthNavigation.ResetPassword.Confirmation.route) }
            )
        }
        composable(route = Screens.AuthNavigation.ResetPassword.Confirmation.route) {
            val resetPasswordViewModel = it.sharedViewModel<ResetPasswordViewModel>(navController)
            PasswordConfirmation(
                uiState = resetPasswordViewModel.resetUiState,
                onBackToLogin = { navController.navigate(Screens.AuthNavigation.Login.route) },
                recoverResponse = resetPasswordViewModel.recoverResponse,
                onEnteringVerificationCode = resetPasswordViewModel::onEnteringVerificationCode,
                verifyVerificationCode = resetPasswordViewModel::verifyVerificationCode,
                onEnteringPassword = resetPasswordViewModel::onEnteringPassword,
                onReTypingPassword = resetPasswordViewModel::onReTypingPassword,
                onButtonClickedScreen2 = resetPasswordViewModel::onButtonClickedScreen2,
            )
        }
    }
}