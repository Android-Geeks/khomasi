package com.company.khomasi.presentation.navigation.navigators


import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.company.khomasi.navigation.Screens
import com.company.khomasi.presentation.favorite.FavouritePage
import com.company.khomasi.presentation.favorite.FavouriteViewModel
import com.company.khomasi.presentation.home.HomeScreen
import com.company.khomasi.presentation.home.HomeViewModel
import com.company.khomasi.presentation.myBookings.MyBookingPage
import com.company.khomasi.presentation.myBookings.MyBookingViewModel
import com.company.khomasi.presentation.myBookings.components.CancelSheet
import com.company.khomasi.presentation.navigation.components.sharedViewModel
import com.company.khomasi.presentation.playground.PlaygroundScreen
import com.company.khomasi.presentation.playground.PlaygroundViewModel
import com.company.khomasi.presentation.playground.booking.BookingScreen
import com.company.khomasi.presentation.playground.booking.ConfirmBookingScreen
import com.company.khomasi.presentation.profile.EditProfile
import com.company.khomasi.presentation.profile.ProfileViewModel
import com.company.khomasi.presentation.profile.ViewProfile
import com.company.khomasi.presentation.search.SearchQuery
import com.company.khomasi.presentation.search.SearchResult
import com.company.khomasi.presentation.search.SearchViewModel
import com.company.khomasi.presentation.venues.BrowsePlaygrounds
import com.company.khomasi.presentation.venues.BrowsePlaygroundsViewModel
import com.company.khomasi.presentation.venues.BrowseResults
import com.company.khomasi.presentation.venues.FilterResults


fun NavGraphBuilder.khomasiNavigator(navController: NavHostController) {
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
                onFavouriteClick = homeViewModel::onFavouriteClicked
            )
        }

        composable(route = Screens.KhomasiNavigation.Favorite.route) {
            val favouriteViewModel: FavouriteViewModel = hiltViewModel()
            FavouritePage(
                uiState = favouriteViewModel.uiState,
                getFavoritePlaygrounds = favouriteViewModel::getFavoritePlaygrounds,
                onFavouriteClick = favouriteViewModel::onFavouriteClicked,
                onPlaygroundClick = { playgroundId ->
                    favouriteViewModel.onClickPlayground(playgroundId)
                    navController.navigate(Screens.KhomasiNavigation.BookingPlayground.route + "/$playgroundId")
                }
            )
        }


        myBookingsNavigator(navController = navController)

        composable(route = Screens.KhomasiNavigation.Playgrounds.route) {

        }

        searchNavigator(navController)

        profileNavigator(navController)

        playgroundsNavigator(navController)
        bookingPlaygroundNavigator(navController = navController)

    }
}

fun NavGraphBuilder.playgroundsNavigator(navController: NavHostController) {
    navigation(
        route = Screens.KhomasiNavigation.Playgrounds.route,
        startDestination = Screens.KhomasiNavigation.Playgrounds.BrowsePlaygrounds.route
    ) {
        composable(route = Screens.KhomasiNavigation.Playgrounds.BrowsePlaygrounds.route) {
            val browsePlaygroundsViewModel =
                it.sharedViewModel<BrowsePlaygroundsViewModel>(navController = navController)
            BrowsePlaygrounds(
                localUser = browsePlaygroundsViewModel.localUser,
                filteredPlayground = browsePlaygroundsViewModel.filteredPlaygrounds,
                browseUiState = browsePlaygroundsViewModel.uiState,
                getFilteredPlaygrounds = { browsePlaygroundsViewModel.getPlaygrounds() },
                onFilterClick = { navController.navigate(Screens.KhomasiNavigation.Playgrounds.FilterPlaygrounds.route) },
                onFavouriteClicked = browsePlaygroundsViewModel::onFavouriteClicked,
                onClickPlaygroundCard = { playgroundId ->
                    navController.navigate(Screens.KhomasiNavigation.BookingPlayground.route + "/$playgroundId")
                }
            )
        }

        composable(route = Screens.KhomasiNavigation.Playgrounds.FilterPlaygrounds.route) {
            val browsePlaygroundsViewModel =
                it.sharedViewModel<BrowsePlaygroundsViewModel>(navController = navController)
            FilterResults(
                filteredUiState = browsePlaygroundsViewModel.uiState,
                onBackClick = {
                    navController.popBackStack()
                    browsePlaygroundsViewModel.onResetFilters()
                },
                onShowFiltersClicked = { s, d ->
                    browsePlaygroundsViewModel.onShowFiltersClicked(s, d)
                    navController.navigate(Screens.KhomasiNavigation.Playgrounds.ResultPlaygrounds.route)
                },
                onResetFilters = browsePlaygroundsViewModel::onResetFilters,
                updateDuration = browsePlaygroundsViewModel::updateDuration,
                setPrice = browsePlaygroundsViewModel::setPrice,
                setBookingTime = browsePlaygroundsViewModel::setBookingTime,
                updateType = browsePlaygroundsViewModel::updateType,
            )
        }
        composable(route = Screens.KhomasiNavigation.Playgrounds.ResultPlaygrounds.route) {
            val browsePlaygroundsViewModel =
                it.sharedViewModel<BrowsePlaygroundsViewModel>(navController = navController)
            BrowseResults(
                browseUiState = browsePlaygroundsViewModel.uiState,
                onBackClicked = { navController.popBackStack() },
                onFavClicked = browsePlaygroundsViewModel::onFavouriteClicked,
                onClickPlayground = { playgroundId ->
                    navController.navigate(Screens.KhomasiNavigation.BookingPlayground.route + "/$playgroundId")
                })
        }
    }
}

fun NavGraphBuilder.bookingPlaygroundNavigator(navController: NavHostController) {
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
                reviewsState = playgroundViewModel.reviewsState,
                onViewRatingClicked = playgroundViewModel::updateShowReviews,
                onClickBack = { navController.popBackStack() },
                onClickShare = {},
                onClickFav = playgroundViewModel::onClickFavourite,
                onBookNowClicked = {
                    navController.navigate(
                        Screens.KhomasiNavigation.BookingPlayground.BookingDetails.route
                    )
                },
                onClickDisplayOnMap = {},
                getPlaygroundDetails = playgroundViewModel::getPlaygroundDetails,
                updateShowReview = playgroundViewModel::updateShowReviews
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
                updateDuration = bookingViewModel::updateDuration,
                getFreeSlots = bookingViewModel::getFreeTimeSlots,
                updateSelectedDay = bookingViewModel::updateSelectedDay,
                onSlotClicked = bookingViewModel::onSlotClicked,
                checkValidity = bookingViewModel::checkSlotsConsecutive,
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

        composable(route = Screens.KhomasiNavigation.BookingPlayground.Payment.route) {

        }
    }
}

fun NavGraphBuilder.myBookingsNavigator(navController: NavHostController) {
    navigation(
        route = Screens.KhomasiNavigation.MyBookings.route,
        startDestination = Screens.KhomasiNavigation.MyBookings.BookingHistory.route
    ) {
        composable(route = Screens.KhomasiNavigation.MyBookings.BookingHistory.route) {
            val bookingViewModel =
                it.sharedViewModel<MyBookingViewModel>(navController = navController)
            MyBookingPage(
                uiState = bookingViewModel.uiState,
                myBookingPlaygrounds = bookingViewModel::myBookingPlaygrounds,
                onClickPlaygroundCard = { playgroundId ->
                    bookingViewModel.onClickPlayground(playgroundId)
                    navController.navigate(Screens.KhomasiNavigation.MyBookings.CancelBooking.route)
                },
                playgroundReview = bookingViewModel::playgroundReview,
                onRatingChange = bookingViewModel::onRatingChange,
                onCommentChange = bookingViewModel::onCommentChange,
                reBook = { playgroundId ->
                    bookingViewModel.reBook(playgroundId)
                    navController.navigate(Screens.KhomasiNavigation.BookingPlayground.BookingDetails.route + "/$playgroundId")
                },
                onClickBookField = { navController.navigate(Screens.KhomasiNavigation.Playgrounds.route) }
            )
        }
        composable(route = Screens.KhomasiNavigation.MyBookings.CancelBooking.route) {
            val myBookingViewModel =
                it.sharedViewModel<MyBookingViewModel>(navController = navController)
            CancelSheet(
                onBackClick = { navController.popBackStack() },
                uiState = myBookingViewModel.uiState,
                cancelBooking = myBookingViewModel::cancelBooking
            )
        }
    }
}


fun NavGraphBuilder.profileNavigator(navController: NavHostController) {
    navigation(
        route = Screens.KhomasiNavigation.Profile.route,
        startDestination = Screens.KhomasiNavigation.Profile.ViewProfile.route
    ) {
        composable(route = Screens.KhomasiNavigation.Profile.ViewProfile.route) {
            val profileViewModel =
                it.sharedViewModel<ProfileViewModel>(navController = navController)
            ViewProfile(
                profileUiState = profileViewModel.profileUiState,
                localUserUiState = profileViewModel.localUser,
                getProfileImage = profileViewModel::getProfileImage,
                onEditProfile = {
                    navController.navigate(Screens.KhomasiNavigation.Profile.EditProfile.route)
                },
                onFeedbackCategorySelected = profileViewModel::onFeedbackCategorySelected,
                onFeedbackChanged = profileViewModel::onFeedbackChanged,
                onLogout = profileViewModel::onLogout,
                updateUserData = profileViewModel::updateUserData,
                onBackClick = { navController.popBackStack() },
                sendFeedback = profileViewModel::sendFeedback
            )
        }
        composable(route = Screens.KhomasiNavigation.Profile.EditProfile.route) {
            val profileViewModel =
                it.sharedViewModel<ProfileViewModel>(navController = navController)
            EditProfile(
                editProfileUiState = profileViewModel.profileUiState,
                onSaveProfile = profileViewModel::onSaveProfile,
                onFirstNameChange = profileViewModel::onFirstNameChanged,
                onLastNameChange = profileViewModel::onLastNameChanged,
                onPhoneChange = profileViewModel::onPhoneChanged,
                onChangeProfileImage = profileViewModel::onChangeProfileImage,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

fun NavGraphBuilder.searchNavigator(navController: NavHostController) {
    navigation(
        route = Screens.KhomasiNavigation.Search.route,
        startDestination = Screens.KhomasiNavigation.Search.SearchQuery.route
    ) {
        composable(route = Screens.KhomasiNavigation.Search.SearchQuery.route) {
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
                navigateToPlaygroundDetails = { playgroundId -> navController.navigate(Screens.KhomasiNavigation.BookingPlayground.route + "/$playgroundId") },
                onBackClick = { navController.popBackStack() },
                onNextPage = { navController.navigate(Screens.KhomasiNavigation.Search.SearchResults.route) },
            )
        }

        composable(route = Screens.KhomasiNavigation.Search.SearchResults.route) {
            val searchViewModel = it.sharedViewModel<SearchViewModel>(navController = navController)
            SearchResult(
                searchUiState = searchViewModel.uiState,
                onBackClick = { navController.popBackStack() },
                navigateToPlaygroundDetails = { playgroundId -> navController.navigate(Screens.KhomasiNavigation.BookingPlayground.route + "/$playgroundId") },
                onSearchFilterChanged = searchViewModel::onSearchFilterChanged,
                onBackPage = { navController.popBackStack() },
            )
        }
    }

}