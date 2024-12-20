package com.company.rentafield.presentation.navigation.navigators


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.company.rentafield.presentation.navigation.components.Screens
import com.company.rentafield.presentation.navigation.components.sharedViewModel
import com.company.rentafield.presentation.screens.ai.AiScreen
import com.company.rentafield.presentation.screens.booking.BookingScreen
import com.company.rentafield.presentation.screens.booking.BookingViewModel
import com.company.rentafield.presentation.screens.booking.ConfirmBookingScreen
import com.company.rentafield.presentation.screens.booking.PaymentScreen
import com.company.rentafield.presentation.screens.favorite.FavouriteReducer
import com.company.rentafield.presentation.screens.favorite.FavouriteScreen
import com.company.rentafield.presentation.screens.home.HomeReducer
import com.company.rentafield.presentation.screens.home.HomeScreen
import com.company.rentafield.presentation.screens.mybookings.MyBookingScreen
import com.company.rentafield.presentation.screens.mybookings.MyBookingViewModel
import com.company.rentafield.presentation.screens.mybookings.components.CancelBookingPage
import com.company.rentafield.presentation.screens.notifications.NotificationViewModel
import com.company.rentafield.presentation.screens.notifications.NotificationsScreen
import com.company.rentafield.presentation.screens.playground.PlaygroundScreen
import com.company.rentafield.presentation.screens.playground.PlaygroundViewModel
import com.company.rentafield.presentation.screens.profile.EditProfile
import com.company.rentafield.presentation.screens.profile.ProfileViewModel
import com.company.rentafield.presentation.screens.profile.ViewProfile
import com.company.rentafield.presentation.screens.search.SearchQuery
import com.company.rentafield.presentation.screens.search.SearchResult
import com.company.rentafield.presentation.screens.search.SearchViewModel
import com.company.rentafield.presentation.screens.venues.BrowsePlaygrounds
import com.company.rentafield.presentation.screens.venues.BrowsePlaygroundsViewModel
import com.company.rentafield.presentation.screens.venues.BrowseResults
import com.company.rentafield.presentation.screens.venues.FilterResults


@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.rentAfieldNavigator(navController: NavHostController) {
    navigation(
        route = Screens.RentafieldNavigation.route,
        startDestination = Screens.RentafieldNavigation.Home.route
    ) {
        composable(route = Screens.RentafieldNavigation.Home.route) {
            HomeScreen(
                onNavigate = { action ->
                    when (action) {
                        is HomeReducer.Effect.NavigateToAiService -> {
                            navController.navigate(Screens.RentafieldNavigation.AiService.route + "/${action.userId}")
                        }

                        is HomeReducer.Effect.NavigateToBrowsePlaygrounds -> {
                            navController.navigate(Screens.RentafieldNavigation.Playgrounds.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true

                            }
                        }

                        is HomeReducer.Effect.NavigateToNotifications -> navController.navigate(
                            Screens.RentafieldNavigation.Notifications.route
                        )

                        is HomeReducer.Effect.NavigateToPlaygroundDetails -> navController.navigate(
                            Screens.RentafieldNavigation.BookingPlayground.route + "/${action.playgroundId}" + "/${action.isFavourite}"
                        )

                        is HomeReducer.Effect.NavigateToProfile -> navController.navigate(Screens.RentafieldNavigation.Profile.route)

                        is HomeReducer.Effect.NavigateToSearch -> navController.navigate(Screens.RentafieldNavigation.Search.route)

                        else -> Unit
                    }
                }
            )
        }

        composable(route = Screens.RentafieldNavigation.Notifications.route) {
            val notificationViewModel: NotificationViewModel = hiltViewModel()
            NotificationsScreen(
                onBackClicked = navController::navigateUp,
                notificationStateFlow = notificationViewModel.notificationState,
                localUserUiState = notificationViewModel.localUser,
                getNotifications = { notificationViewModel.getAiResult() }
            )
        }

        composable(route = Screens.RentafieldNavigation.Favorite.route) {
            FavouriteScreen(
                onNavigate = { action ->
                    when (action) {
                        is FavouriteReducer.Effect.NavigateToPlaygroundDetails -> {
                            navController.navigate(Screens.RentafieldNavigation.BookingPlayground.route + "/${action.playgroundId}" + "/${action.isFavourite}")
                        }

                        is FavouriteReducer.Effect.NavigateBack -> navController.navigateUp()

                        else -> Unit
                    }
                }
            )
        }

        composable(route = Screens.RentafieldNavigation.AiService.route + "/{userId}") { navBackStackEntry ->
            val userId = navBackStackEntry.arguments?.getString("userId") ?: ""

            AiScreen(
                onBackClicked = navController::navigateUp,
                userId = userId
            )
        }

        myBookingsNavigator(navController = navController)

        searchNavigator(navController)

        profileNavigator(navController)

        playgroundsNavigator(navController)

        bookingPlaygroundNavigator(navController)

    }
}

fun NavGraphBuilder.playgroundsNavigator(navController: NavHostController) {
    navigation(
        route = Screens.RentafieldNavigation.Playgrounds.route,
        startDestination = Screens.RentafieldNavigation.Playgrounds.BrowsePlaygrounds.route
    ) {
        composable(route = Screens.RentafieldNavigation.Playgrounds.BrowsePlaygrounds.route) {
            val browsePlaygroundsViewModel =
                it.sharedViewModel<BrowsePlaygroundsViewModel>(navController = navController)
            BrowsePlaygrounds(
                localUser = browsePlaygroundsViewModel.localUser,
                filteredPlayground = browsePlaygroundsViewModel.filteredPlaygrounds,
                browseUiState = browsePlaygroundsViewModel.uiState,
                getFilteredPlaygrounds = { browsePlaygroundsViewModel.getPlaygrounds() },
                onFilterClick = { navController.navigate(Screens.RentafieldNavigation.Playgrounds.FilterPlaygrounds.route) },
                onFavouriteClicked = browsePlaygroundsViewModel::onFavouriteClicked,
                onClickPlaygroundCard = { playgroundId, isFavourite ->
                    navController.navigate(Screens.RentafieldNavigation.BookingPlayground.route + "/$playgroundId" + "/$isFavourite")
                }
            )
        }

        composable(route = Screens.RentafieldNavigation.Playgrounds.FilterPlaygrounds.route) {
            val browsePlaygroundsViewModel =
                it.sharedViewModel<BrowsePlaygroundsViewModel>(navController = navController)
            FilterResults(
                filteredUiState = browsePlaygroundsViewModel.uiState,
                onBackClick = {
                    navController.navigateUp()
                    browsePlaygroundsViewModel.onResetFilters()
                },
                onShowFiltersClicked = { s, d ->
                    browsePlaygroundsViewModel.onShowFiltersClicked(s, d)
                    navController.navigate(Screens.RentafieldNavigation.Playgrounds.ResultPlaygrounds.route)
                },
                onResetFilters = browsePlaygroundsViewModel::onResetFilters,
                updateDuration = browsePlaygroundsViewModel::updateDuration,
                setPrice = browsePlaygroundsViewModel::setPrice,
                setBookingTime = browsePlaygroundsViewModel::setBookingTime,
                updateType = browsePlaygroundsViewModel::updateType,
            )
        }
        composable(route = Screens.RentafieldNavigation.Playgrounds.ResultPlaygrounds.route) {
            val browsePlaygroundsViewModel =
                it.sharedViewModel<BrowsePlaygroundsViewModel>(navController = navController)
            BrowseResults(
                browseUiState = browsePlaygroundsViewModel.uiState,
                onBackClicked = navController::navigateUp,
                onFavClicked = browsePlaygroundsViewModel::onFavouriteClicked,
                onClickPlayground = { playgroundId, isFavourite ->
                    navController.navigate(Screens.RentafieldNavigation.BookingPlayground.route + "/$playgroundId" + "/$isFavourite")
                }
            )
        }
    }
}

fun NavGraphBuilder.bookingPlaygroundNavigator(navController: NavHostController) {
    navigation(
        route = Screens.RentafieldNavigation.BookingPlayground.route + "/{playgroundId}" + "/{isFavourite}",
        startDestination = Screens.RentafieldNavigation.BookingPlayground.PlaygroundDetails.route
    ) {
        composable(route = Screens.RentafieldNavigation.BookingPlayground.PlaygroundDetails.route) { navBackStackEntry ->
            val playgroundId = navBackStackEntry.arguments?.getString("playgroundId")?.toInt()
            val isFavourite = navBackStackEntry.arguments?.getString("isFavourite")?.toBoolean()
            val playgroundViewModel =
                navBackStackEntry.sharedViewModel<PlaygroundViewModel>(navController = navController)
            PlaygroundScreen(
                playgroundId = playgroundId ?: 1,
                isFavourite = isFavourite ?: false,
                playgroundStateFlow = playgroundViewModel.playgroundState,
                playgroundInfoUiState = playgroundViewModel.infoUiState,
                playgroundReviewsUiState = playgroundViewModel.reviewsUiState,
                reviewsState = playgroundViewModel.reviewsState,
                onViewRatingClicked = playgroundViewModel::updateShowReviews,
                updateFavouriteAndPlaygroundId = playgroundViewModel::updateFavouriteAndPlaygroundId,
                onClickBack = navController::navigateUp,
                onClickShare = {},
//                onClickFav = playgroundViewModel::updateUserFavourite,
                onBookNowClicked = {
                    navController.navigate(
                        (Screens.RentafieldNavigation.BookingPlayground.BookingDetails.route + "/$playgroundId")
                    )
                },
                getPlaygroundDetails = playgroundViewModel::getPlaygroundDetails,
                updateShowReview = playgroundViewModel::updateShowReviews
            )
        }

        composable(
            route = Screens.RentafieldNavigation.BookingPlayground.BookingDetails.route + "/{playgroundId}",
        ) { navBackStackEntry ->
            val bookingViewModel =
                navBackStackEntry.sharedViewModel<BookingViewModel>(navController = navController)
            val playgroundId = navBackStackEntry.arguments?.getString("playgroundId")?.toInt()
            BookingScreen(
                bookingUiState = bookingViewModel.bookingUiState,
                playgroundId = playgroundId ?: 1,
                freeSlotsState = bookingViewModel.freeSlotsState,
                onBackClicked = navController::navigateUp,
                updateDuration = bookingViewModel::updateDuration,
                getFreeSlots = bookingViewModel::getFreeTimeSlots,
                updateSelectedDay = bookingViewModel::updateSelectedDay,
                onSlotClicked = bookingViewModel::onSlotClicked,
                checkValidity = bookingViewModel::checkSlotsConsecutive,
                onNextToConfirmationClicked = {
                    navController.navigate(Screens.RentafieldNavigation.BookingPlayground.BookingConfirmation.route)
                    bookingViewModel.updateBookingTime()
                },
                getPlaygroundDetails = bookingViewModel::getPlaygroundDetails
            )
        }

        composable(route = Screens.RentafieldNavigation.BookingPlayground.BookingConfirmation.route) {

            val bookingViewModel =
                it.sharedViewModel<BookingViewModel>(navController = navController)

            ConfirmBookingScreen(
                bookingUiState = bookingViewModel.bookingUiState,
                onBackClicked = navController::navigateUp,
                onNextToPaymentClicked = { navController.navigate(Screens.RentafieldNavigation.BookingPlayground.Payment.route) },
            )
        }

        composable(route = Screens.RentafieldNavigation.BookingPlayground.Payment.route) { navBack ->
            val bookingViewModel =
                navBack.sharedViewModel<BookingViewModel>(navController = navController)
            PaymentScreen(
                paymentUiState = bookingViewModel.paymentUiState,
                bookingPlaygroundResponse = bookingViewModel.bookingResponse,
                updateCardNumber = bookingViewModel::updateCardNumber,
                updateCardValidationDate = bookingViewModel::updateCardValidationDate,
                updateCardCvv = bookingViewModel::updateCardCvv,
                onPayWithVisaClicked = { },
                onPayWithCoinsClicked = { bookingViewModel.bookingPlayground() },
                onBackClicked = navController::navigateUp,
                onBookingSuccess = { navController.navigate(Screens.RentafieldNavigation.Home.route) }
            )
        }
    }
}

fun NavGraphBuilder.myBookingsNavigator(navController: NavHostController) {
    navigation(
        route = Screens.RentafieldNavigation.MyBookings.route,
        startDestination = Screens.RentafieldNavigation.MyBookings.BookingHistory.route
    ) {
        composable(route = Screens.RentafieldNavigation.MyBookings.BookingHistory.route) {
            val bookingViewModel =
                it.sharedViewModel<MyBookingViewModel>(navController = navController)
            MyBookingScreen(
                uiState = bookingViewModel.uiState,
                myBookingsState = bookingViewModel.myBookingState,
                reviewState = bookingViewModel.reviewState,
                myBookingPlaygrounds = bookingViewModel::myBookingPlaygrounds,
                onClickPlaygroundCard = { playgroundId ->
                    bookingViewModel.onClickPlayground(playgroundId)
                    navController.navigate(Screens.RentafieldNavigation.MyBookings.CancelBooking.route)
                },
                playgroundReview = bookingViewModel::playgroundReview,
                onRatingChange = bookingViewModel::onRatingChange,
                onCommentChange = bookingViewModel::onCommentChange,

                reBook = { playgroundId, isFavourite ->
                    navController.navigate(Screens.RentafieldNavigation.BookingPlayground.route + "/$playgroundId" + "/$isFavourite")
                },
                onClickBookField = { navController.navigate(Screens.RentafieldNavigation.Playgrounds.route) },
                cancelDetails = { playgroundId, isFavourite ->
                    navController.navigate(Screens.RentafieldNavigation.BookingPlayground.route + "/$playgroundId" + "/$isFavourite")
                },
            )
        }
        composable(route = Screens.RentafieldNavigation.MyBookings.CancelBooking.route) {
            val myBookingViewModel =
                it.sharedViewModel<MyBookingViewModel>(navController = navController)
            CancelBookingPage(
                onBackClick = navController::navigateUp,
                uiState = myBookingViewModel.uiState,
                cancelBooking = myBookingViewModel::cancelBooking
            )
        }
    }
}


fun NavGraphBuilder.profileNavigator(navController: NavHostController) {
    navigation(
        route = Screens.RentafieldNavigation.Profile.route,
        startDestination = Screens.RentafieldNavigation.Profile.ViewProfile.route
    ) {
        composable(route = Screens.RentafieldNavigation.Profile.ViewProfile.route) {
            val profileViewModel =
                it.sharedViewModel<ProfileViewModel>(navController = navController)
            ViewProfile(
                profileUiState = profileViewModel.profileUiState,
                localUserUiState = profileViewModel.localUser,
                getProfileImage = profileViewModel::getProfileImage,
                onEditProfile = {
                    navController.navigate(Screens.RentafieldNavigation.Profile.EditProfile.route)
                },
                onFeedbackCategorySelected = profileViewModel::onFeedbackCategorySelected,
                onFeedbackChanged = profileViewModel::onFeedbackChanged,
                onLogout = profileViewModel::onLogout,
                updateUserData = profileViewModel::updateUserData,
                onBackClick = navController::navigateUp,
                sendFeedback = profileViewModel::sendFeedback
            )
        }
        composable(route = Screens.RentafieldNavigation.Profile.EditProfile.route) {
            val profileViewModel =
                it.sharedViewModel<ProfileViewModel>(navController = navController)
            EditProfile(
                editProfileUiState = profileViewModel.profileUiState,
                onSaveProfile = profileViewModel::onSaveProfile,
                onFirstNameChange = profileViewModel::onFirstNameChanged,
                onLastNameChange = profileViewModel::onLastNameChanged,
                onPhoneChange = profileViewModel::onPhoneChanged,
                onChangeProfileImage = profileViewModel::onChangeProfileImage,
                onBackClick = navController::navigateUp
            )
        }
    }
}

fun NavGraphBuilder.searchNavigator(navController: NavHostController) {
    navigation(
        route = Screens.RentafieldNavigation.Search.route,
        startDestination = Screens.RentafieldNavigation.Search.SearchQuery.route
    ) {
        composable(route = Screens.RentafieldNavigation.Search.SearchQuery.route) {
            val searchViewModel = it.sharedViewModel<SearchViewModel>(navController = navController)
            SearchQuery(
                playgroundsState = searchViewModel.searchResults,
                localUserState = searchViewModel.localUser,
                searchQuery = searchViewModel.searchQuery,
                searchUiState = searchViewModel.uiState,
                getSearchData = searchViewModel::getSearchData,
                onQueryChange = searchViewModel::onSearchQueryChanged,
                onSearchQuerySubmitted = searchViewModel::onSearchQuerySubmitted,
                onClearHistory = searchViewModel::onClickRemoveSearchHistory,
                navigateToPlaygroundDetails = { playgroundId, isFavourite ->
                    navController.navigate(
                        Screens.RentafieldNavigation.BookingPlayground.route + "/$playgroundId" + "/$isFavourite"
                    )
                },
                onBackClick = navController::navigateUp,
                onNextPage = { navController.navigate(Screens.RentafieldNavigation.Search.SearchResults.route) },
            )
        }

        composable(route = Screens.RentafieldNavigation.Search.SearchResults.route) {
            val searchViewModel = it.sharedViewModel<SearchViewModel>(navController = navController)
            SearchResult(
                searchUiState = searchViewModel.uiState,
                onBackClick = navController::navigateUp,
                navigateToPlaygroundDetails = { playgroundId, isFavourite ->
                    navController.navigate(
                        Screens.RentafieldNavigation.BookingPlayground.route + "/$playgroundId" + "/$isFavourite"
                    )
                },
                onSearchFilterChanged = searchViewModel::onSearchFilterChanged,
                onBackPage = navController::navigateUp,
            )
        }
    }

}