package com.company.khomasi.navigation

sealed class Screens(
    val route: String,
) {
    data object AppStartNavigation : Screens("AppStartNavigation") {
        data object OnBoarding : Screens("OnBoarding")
    }

    data object AuthNavigation : Screens("AuthNavigation") {
        data object LoginOrRegister : Screens("LoginOrRegister")
        data object Login : Screens("Login")
        data object Register : Screens("Register") {
            data object NameAndPhone : Screens("NameAndPhone")
            data object EmailAndPassword : Screens("EmailAndPassword")
        }

        data object ResetPassword : Screens("ResetPassword") {
            data object Email : Screens("ResetPasswordEmail")
            data object Confirmation : Screens("ResetPasswordOTP")
        }

        data object OTP : Screens("OTP")
    }

    data object KhomasiNavigation : Screens("KhomasiNavigation") {
        data object Home : Screens("Home")
        data object MyBookings : Screens("MyBookings") {
            data object BookingHistory : Screens("BookingHistory")
            data object CancelBooking : Screens("CancelBooking")
        }

        data object Playgrounds : Screens("Playgrounds") {
            data object BrowsePlaygrounds : Screens("BrowsePlaygrounds")
            data object FilterPlaygrounds : Screens("FilterPlaygrounds")
            data object ResultPlaygrounds : Screens("ResultPlaygrounds")
        }

        data object Favorite : Screens("Favorite")
        data object Profile : Screens("Profile") {
            data object ViewProfile : Screens("ViewProfile")
            data object EditProfile : Screens("EditProfile")
        }

        data object Search : Screens("Search") {
            data object SearchQuery : Screens("SearchQuery")
            data object SearchResults : Screens("SearchResults")
        }

        data object BookingPlayground : Screens(
            "BookingPlayground"
        ) {
            data object PlaygroundDetails : Screens("PlaygroundDetails")
            data object BookingDetails : Screens("BookingDetails")
            data object BookingConfirmation : Screens("BookingConfirmation")
            data object Payment : Screens("Payment")
        }

    }
}