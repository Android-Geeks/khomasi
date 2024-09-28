package com.company.rentafield.presentation.screens.login

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.company.rentafield.R
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
        sealed class Error(@StringRes val message: Int) : Effect() {
            data object InvalidEmailOrPassword : Error(R.string.invalid_email_or_password)
            data object InvalidPassword : Error(R.string.invalid_password)
            data object EmailNotConfirmed : Error(R.string.email_not_confirmed)
            data object UserNotFound : Error(R.string.user_not_found)
            data object UserLoginOnly : Error(R.string.login_only_message)
            data object Unknown : Error(R.string.something_went_wrong)
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