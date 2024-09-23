package com.company.rentafield.presentation.navigation.navigators

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.company.rentafield.presentation.navigation.components.Screens
import com.company.rentafield.presentation.navigation.components.sharedViewModel
import com.company.rentafield.presentation.screens.login.LoginReducer
import com.company.rentafield.presentation.screens.login.LoginScreen
import com.company.rentafield.presentation.screens.loginOrSignup.LoginOrRegisterScreen
import com.company.rentafield.presentation.screens.otp.OtpScreen
import com.company.rentafield.presentation.screens.otp.OtpViewModel
import com.company.rentafield.presentation.screens.register.RegisterEmailAndPassword
import com.company.rentafield.presentation.screens.register.RegisterNameAndPhone
import com.company.rentafield.presentation.screens.register.RegisterViewModel
import com.company.rentafield.presentation.screens.resetPassword.EmailVerification
import com.company.rentafield.presentation.screens.resetPassword.PasswordConfirmation
import com.company.rentafield.presentation.screens.resetPassword.ResetPasswordViewModel


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
            LoginScreen(
                onNavigate = {
                    when (it) {
                        LoginReducer.Effect.NavigateToHelpAndSupport -> Unit
                        LoginReducer.Effect.NavigateToPrivacyAndPolicy -> Unit
                        LoginReducer.Effect.NavigateToRegister -> navController.navigate(Screens.AuthNavigation.Register.route)
                        LoginReducer.Effect.NavigateToResetPassword -> navController.navigate(
                            Screens.AuthNavigation.ResetPassword.route
                        )

                        else -> Unit
                    }
                }
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
                updateLoading = registerViewModel::updateLoading
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
                onCancelClick = navController::navigateUp,
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