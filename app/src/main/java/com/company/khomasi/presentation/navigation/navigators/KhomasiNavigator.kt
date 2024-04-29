package com.company.khomasi.presentation.navigation.navigators


import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.company.khomasi.navigation.Screens
import com.company.khomasi.presentation.favorite.FavouritePage
import com.company.khomasi.presentation.favorite.FavouriteViewModel
import com.company.khomasi.presentation.home.HomeScreen
import com.company.khomasi.presentation.home.HomeViewModel
import com.company.khomasi.presentation.navigation.components.sharedViewModel
import com.company.khomasi.presentation.playground.PlaygroundScreen
import com.company.khomasi.presentation.playground.PlaygroundViewModel
import com.company.khomasi.presentation.playground.booking.BookingScreen
import com.company.khomasi.presentation.playground.booking.ConfirmBookingScreen
import com.company.khomasi.presentation.playground.components.PlaygroundReviews
import com.company.khomasi.presentation.profile.ProfileScreen
import com.company.khomasi.presentation.profile.ProfileViewModel
import com.company.khomasi.presentation.search.SearchScreen
import com.company.khomasi.presentation.search.SearchViewModel


fun NavGraphBuilder.khomasiNavigator(navController: NavController) {
    navigation(
        route = Screens.KhomasiNavigation.route,
        startDestination = Screens.KhomasiNavigation.Home.route
    ) {
        composable(route = Screens.KhomasiNavigation.Home.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                playgroundsState = homeViewModel.playgroundState,
                homeUiState = homeViewModel.homeUiState,
                localUserState = homeViewModel.localUser,
                onClickUserImage = { navController.navigate(Screens.KhomasiNavigation.Profile.route) },
                onClickPlaygroundCard = { playgroundId ->
                    navController.navigate(Screens.KhomasiNavigation.BookingPlayground.route + "/$playgroundId")
                },
                getHomeScreenData = homeViewModel::getHomeScreenData,
                onClickBell = { /* will nav to notification page */ },
                onClickViewAll = { homeViewModel.onClickViewAll() },
                onSearchBarClicked = { navController.navigate(Screens.KhomasiNavigation.Search.route) },
                onAdClicked = {},
                onFavouriteClick = homeViewModel::onFavouriteClicked,
            )
        }
        composable(route = Screens.KhomasiNavigation.Favorite.route) {
            val favouriteViewModel: FavouriteViewModel = hiltViewModel()
            FavouritePage(uiState = favouriteViewModel.uiState,
                getFavoritePlaygrounds = favouriteViewModel::getFavoritePlaygrounds,
                onFavouriteClick = favouriteViewModel::onFavouriteClicked,
                onPlaygroundClick = { playgroundId ->
                    favouriteViewModel.onClickPlayground(playgroundId)
                    navController.navigate(Screens.KhomasiNavigation.BookingPlayground.route + "/$playgroundId")
                })

        }

        myBookingsNavigator(navController = navController)

        composable(route = Screens.KhomasiNavigation.Playgrounds.route) {

        }

        composable(route = Screens.KhomasiNavigation.Search.route) {
            val searchViewModel: SearchViewModel = hiltViewModel()
            SearchScreen(
                onBackClick = { navController.popBackStack() },
                searchQuery = searchViewModel.searchQuery,
                searchUiState = searchViewModel.uiState,
                playgroundsState = searchViewModel.searchResults,
                onQueryChange = searchViewModel::onSearchQueryChanged,
                onSearchQuerySubmitted = searchViewModel::onSearchQuerySubmitted,
                onSearchFilterChanged = searchViewModel::onSearchFilterChanged,
                onClearHistory = searchViewModel::onClickRemoveSearchHistory,
                navigateToPlaygroundDetails = {},
                onBackPage = searchViewModel::onBackPage,
                onNextPage = searchViewModel::onNextPage,
            )
        }
        composable(route = Screens.KhomasiNavigation.Profile.route) {
            val profileViewModel: ProfileViewModel = hiltViewModel()
            ProfileScreen(
                profileUiState = profileViewModel.profileUiState,
                localUserUiState = profileViewModel.localUser,
                getProfileImage = profileViewModel::getProfileImage,
                onEditProfile = profileViewModel::onEditProfile,
                onSaveProfile = profileViewModel::onSaveProfile,
                onFeedbackCategorySelected = profileViewModel::onFeedbackCategorySelected,
                onFeedbackChanged = profileViewModel::onFeedbackChanged,
                onLogout = profileViewModel::onLogout,
                updateUserData = profileViewModel::updateUserData,
                onFirstNameChanged = profileViewModel::onFirstNameChanged,
                onLastNameChanged = profileViewModel::onLastNameChanged,
                onPhoneChanged = profileViewModel::onPhoneChanged,
                onBackClick = { navController.popBackStack() },
                onChangeProfileImage = profileViewModel::onChangeProfileImage,
                sendFeedback = profileViewModel::sendFeedback
            )
        }
        bookingPlaygroundNavigator(navController = navController)

    }
}

fun NavGraphBuilder.bookingPlaygroundNavigator(navController: NavController) {
    navigation(
        route = Screens.KhomasiNavigation.BookingPlayground.route + "/{playgroundId}",
        startDestination = Screens.KhomasiNavigation.BookingPlayground.PlaygroundDetails.route
    ) {
        composable(route = Screens.KhomasiNavigation.BookingPlayground.PlaygroundDetails.route) { navBackStackEntry ->
            val playgroundId = navBackStackEntry.arguments?.getString("playgroundId")
            val playgroundViewModel =
                navBackStackEntry.sharedViewModel<PlaygroundViewModel>(navController = navController)
            PlaygroundScreen(
                playgroundId = playgroundId?.toInt() ?: 1,
                playgroundStateFlow = playgroundViewModel.playgroundState,
                playgroundUiState = playgroundViewModel.uiState,
                onViewRatingClicked = { navController.navigate(Screens.KhomasiNavigation.BookingPlayground.PlaygroundReviews.route) },
                onClickBack = { navController.popBackStack() },
                onClickShare = {},
                onClickFav = { playgroundViewModel.onClickFavourite() },
                onBookNowClicked = {
                    navController.navigate(
                        Screens.KhomasiNavigation.BookingPlayground.BookingDetails.route
                    )
                },
                onClickDisplayOnMap = {},
                getPlaygroundDetails = {
                    playgroundViewModel.getPlaygroundDetails(it)
                },
            )
        }

        composable(
            route = Screens.KhomasiNavigation.BookingPlayground.BookingDetails.route,
        ) { navBackStackEntry ->
            val bookingViewModel =
                navBackStackEntry.sharedViewModel<PlaygroundViewModel>(navController = navController)
            BookingScreen(bookingUiState = bookingViewModel.bookingUiState,
                freeSlotsState = bookingViewModel.freeSlotsState,
                onBackClicked = { navController.popBackStack() },
                updateDuration = { bookingViewModel.updateDuration(it) },
                getFreeSlots = {
                    bookingViewModel.getFreeTimeSlots()
                },
                updateSelectedDay = { bookingViewModel.updateSelectedDay(it) },
                onSlotClicked = { bookingViewModel.onSlotClicked(it) },
                checkValidity = { bookingViewModel.checkSlotsConsecutive() },
                onNextClicked = {
                    navController.navigate(Screens.KhomasiNavigation.BookingPlayground.BookingConfirmation.route)
                    bookingViewModel.updateBookingTime()
                })
        }

        composable(route = Screens.KhomasiNavigation.BookingPlayground.BookingConfirmation.route) {

            val bookingViewModel =
                it.sharedViewModel<PlaygroundViewModel>(navController = navController)

            ConfirmBookingScreen(
                bookingUiState = bookingViewModel.bookingUiState,
                onBackClicked = { navController.popBackStack() },
                onNextClicked = { },
            )
        }

        composable(route = Screens.KhomasiNavigation.BookingPlayground.PlaygroundReviews.route) {
            val playgroundViewModel =
                it.sharedViewModel<PlaygroundViewModel>(navController = navController)
            PlaygroundReviews(
                getPlaygroundReviews = { playgroundViewModel.getPlaygroundReviews() },
                reviewsState = playgroundViewModel.reviewsState
            )

        }
        composable(route = Screens.KhomasiNavigation.BookingPlayground.Payment.route) {

        }
    }
}

fun NavGraphBuilder.myBookingsNavigator(navController: NavController) {
    navigation(
        route = Screens.KhomasiNavigation.MyBookings.route,
        startDestination = Screens.KhomasiNavigation.MyBookings.BookingHistory.route
    ) {
        composable(route = Screens.KhomasiNavigation.MyBookings.BookingHistory.route) {
            //val bookingViewModel = it.sharedViewModel(navController = navController)
        }
        composable(route = Screens.KhomasiNavigation.MyBookings.CancelBooking.route) {
            //val bookingViewModel = it.sharedViewModel(navController = navController)
        }
    }
}

fun NavGraphBuilder.profileNavigator(navController: NavController) {
    navigation(
        route = Screens.KhomasiNavigation.Profile.route,
        startDestination = Screens.KhomasiNavigation.Profile.ViewProfile.route
    ) {
        composable(route = Screens.KhomasiNavigation.Profile.ViewProfile.route) {
            val profileViewModel =
                it.sharedViewModel<ProfileViewModel>(navController = navController)
            ProfileScreen(
                profileUiState = profileViewModel.profileUiState,
                localUserUiState = profileViewModel.localUser,
                getProfileImage = profileViewModel::getProfileImage,
                onEditProfile = profileViewModel::onEditProfile,
                onSaveProfile = profileViewModel::onSaveProfile,
                onFeedbackCategorySelected = profileViewModel::onFeedbackCategorySelected,
                onFeedbackChanged = profileViewModel::onFeedbackChanged,
                onLogout = profileViewModel::onLogout,
                updateUserData = profileViewModel::updateUserData,
                onFirstNameChanged = profileViewModel::onFirstNameChanged,
                onLastNameChanged = profileViewModel::onLastNameChanged,
                onPhoneChanged = profileViewModel::onPhoneChanged,
                onBackClick = { navController.popBackStack() },
                onChangeProfileImage = profileViewModel::onChangeProfileImage,
                sendFeedback = profileViewModel::sendFeedback
            )
        }
        composable(route = Screens.KhomasiNavigation.Profile.EditProfile.route) {
            val profileViewModel =
                it.sharedViewModel<ProfileViewModel>(navController = navController)

        }
    }
}