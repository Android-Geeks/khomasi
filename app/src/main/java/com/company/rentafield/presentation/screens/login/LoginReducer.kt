package com.company.rentafield.presentation.screens.login

import androidx.compose.runtime.Immutable
import com.company.rentafield.presentation.base.Reducer

class LoginReducer :
    Reducer<LoginReducer.State, LoginReducer.Event, LoginReducer.Effect> {

    @Immutable
    sealed class Event : Reducer.ViewEvent {
        data class UpdateLoading(val isLoading: Boolean) : Event()
        data class UpdateEmail(val email: String) : Event()
        data class UpdatePassword(val password: String) : Event()
        data object ForgotPasswordClick : Event()
        data object RegisterClick : Event()
        data object PrivacyAndPolicyClick : Event()
        data object HelpAndSupportClick : Event()
    }

    @Immutable
    sealed class Effect : Reducer.ViewEffect {
        data object NavigateToRegister : Effect()
        data object NavigateToResetPassword : Effect()
        data object NavigateToPrivacyAndPolicy : Effect()
        data object NavigateToHelpAndSupport : Effect()
        sealed class Error(val message: String) : Effect() {
            data object InvalidEmailOrPassword : Error("Invalid Email or Password")
            data object InvalidPassword : Error("Invalid Password")
            data object EmailNotConfirmed : Error("Email Not Confirmed")
            data object UserNotFound : Error("User Not Found")
            data object UserLoginOnly : Error("User Login Only")
            data object Unknown : Error("Unknown Error")
        }
    }

    @Immutable
    data class State(
        val email: String,
        val password: String,
        val isLoading: Boolean
    ) : Reducer.ViewState

    override fun reduce(
        previousState: State,
        event: Event
    ): Pair<State, Effect?> {
        return when (event) {
            is Event.UpdateLoading -> {
                previousState.copy(isLoading = event.isLoading) to null
            }

            is Event.UpdateEmail -> {
                previousState.copy(email = event.email) to null
            }

            is Event.UpdatePassword -> {
                previousState.copy(password = event.password) to null
            }

            Event.ForgotPasswordClick -> {
                previousState to Effect.NavigateToResetPassword
            }

            Event.HelpAndSupportClick -> {
                previousState to Effect.NavigateToHelpAndSupport
            }

            Event.PrivacyAndPolicyClick -> {
                previousState to Effect.NavigateToPrivacyAndPolicy
            }

            Event.RegisterClick -> {
                previousState to Effect.NavigateToRegister
            }
        }
    }

    companion object {
        fun initial() = State("", "", false)
    }
}