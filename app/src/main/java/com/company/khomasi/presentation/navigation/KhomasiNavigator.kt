package com.company.khomasi.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.company.khomasi.navigation.Screens
import com.company.khomasi.navigation.listOfNavItems
import com.company.khomasi.presentation.favorite.FavouritePage
import com.company.khomasi.presentation.favorite.FavouritePlaygroundsViewModel
import com.company.khomasi.presentation.home.HomeScreen
import com.company.khomasi.presentation.home.HomeViewModel
import com.company.khomasi.presentation.navigation.components.BottomNavigationBar
import com.company.khomasi.presentation.profile.ProfileScreen
import com.company.khomasi.presentation.profile.ProfileViewModel
import com.company.khomasi.presentation.search.SearchScreen
import com.company.khomasi.presentation.search.SearchViewModel


@Composable
fun KhomasiNavigator() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = if (navController.currentDestination?.route
            in listOfNavItems.map { it.route } || navController.currentDestination == null
        ) {
            { BottomNavigationBar(navController) }
        } else {
            {}
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.Home.name,
            modifier = if (navController.currentDestination?.route in listOfNavItems.map { it.route }
                || navController.currentDestination == null) Modifier.padding(
                paddingValues
            ) else Modifier
        ) {
            composable(route = Screens.Home.name) {
                val homeViewModel: HomeViewModel = hiltViewModel()
                HomeScreen(
                    playgroundState = homeViewModel.playgroundState.collectAsState().value,
                    homeUiState = homeViewModel.homeUiState.collectAsState().value,
                    onClickUserImage = {/* will nav to user account */ },
                    onClickBell = { /* will nav to notification page */ },
                    onClickViewAll = { homeViewModel.onClickViewAll() },
                    onSearchBarClicked = {},
                    onAdClicked = {}
                )
            }
            composable(route = Screens.Favorite.name) {
                val favouritePlaygroundsViewModel: FavouritePlaygroundsViewModel = hiltViewModel()
                FavouritePage(
                    fetchUserFavoritePlaygrounds = favouritePlaygroundsViewModel::fetchUserFavoritePlaygrounds,
                    addToFavorites = favouritePlaygroundsViewModel::addToFavorites,
                    removeFromFavorites = favouritePlaygroundsViewModel::removeFromFavorites,
                    uiState = favouritePlaygroundsViewModel.uiState,
                    favouritePlayground = favouritePlaygroundsViewModel.favouritePlaygroundsState,

                    )

            }
            composable(route = Screens.MyBookings.name) {

            }
            composable(route = Screens.Playgrounds.name) {

            }
            composable(route = Screens.Search.name) {
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
            composable(route = Screens.Profile.name) {
                val profileViewModel: ProfileViewModel = hiltViewModel()
                ProfileScreen(
                    profileUiState = profileViewModel.profileUiState,
                    onEditProfile = profileViewModel::onEditProfile,
                    onSaveProfile = profileViewModel::onSaveProfile,
                    onLogout = profileViewModel::onLogout,
                    onBackClick = { navController.popBackStack() },
                )
            }
        }
    }
}