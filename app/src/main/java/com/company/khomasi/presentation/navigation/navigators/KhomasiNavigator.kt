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
import com.company.khomasi.presentation.profile.ProfileScreen
import com.company.khomasi.presentation.profile.EditProfile
import com.company.khomasi.presentation.profile.ProfileViewModel
import com.company.khomasi.presentation.profile.ViewProfile
import com.company.khomasi.presentation.search.SearchQuery
import com.company.khomasi.presentation.search.SearchResult
import com.company.khomasi.presentation.search.SearchViewModel


fun NavGraphBuilder.khomasiNavigator(
    navController: NavController
) {
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
                onClickUserImage = {
                    navController.navigate(Screens.KhomasiNavigation.Profile.route)
                },
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

        searchNavigator(navController)

        profileNavigator(navController)
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
                onSaveProfile = {
                    profileViewModel.onSaveProfile()
                    navController.popBackStack()
                },
                onFirstNameChange = profileViewModel::onFirstNameChanged,
                onLastNameChange = profileViewModel::onLastNameChanged,
                onPhoneChange = profileViewModel::onPhoneChanged,
                onChangeProfileImage = profileViewModel::onChangeProfileImage,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

fun NavGraphBuilder.searchNavigator(navController: NavController) {
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