package com.company.khomasi.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.company.khomasi.navigation.Screens
import com.company.khomasi.navigation.listOfNavItems
import com.company.khomasi.presentation.favorite.FavouritePage
import com.company.khomasi.presentation.favorite.FavouriteViewModel
import com.company.khomasi.presentation.home.HomeScreen
import com.company.khomasi.presentation.home.HomeViewModel
import com.company.khomasi.presentation.myBookings.MyBookingPage
import com.company.khomasi.presentation.myBookings.MyBookingViewModel
import com.company.khomasi.presentation.navigation.components.BottomNavigationBar
import com.company.khomasi.presentation.playground.PlaygroundScreen
import com.company.khomasi.presentation.playground.PlaygroundViewModel
import com.company.khomasi.presentation.profile.ProfileScreen
import com.company.khomasi.presentation.profile.ProfileViewModel
import com.company.khomasi.presentation.search.SearchScreen
import com.company.khomasi.presentation.search.SearchViewModel


@Composable
fun KhomasiNavigator() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        bottomBar =
        {
            BottomNavigationBar(
                navController = navController,
                bottomBarState = navBackStackEntry?.destination?.route
                        in listOfNavItems.map { it.route }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.Home.name,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = Screens.Home.name) {
                val homeViewModel: HomeViewModel = hiltViewModel()
                HomeScreen(
                    playgroundState = homeViewModel.playgroundState.collectAsState().value,
                    homeUiState = homeViewModel.homeUiState.collectAsState().value,
                    onClickUserImage = { navController.navigate(Screens.Profile.name) },
                    onClickPlaygroundCard = { playgroundId ->
                        homeViewModel.onClickPlayground(playgroundId)
                        navController.navigate(Screens.PlaygroundDetails.name)
                    },
                    getPlaygrounds = homeViewModel::getPlaygrounds,
                    onClickBell = { /* will nav to notification page */ },
                    onClickViewAll = { homeViewModel.onClickViewAll() },
                    onSearchBarClicked = { navController.navigate(Screens.Search.name) },
                    onAdClicked = {},
                    onFavouriteClick = homeViewModel::onFavouriteClicked,
                )
            }
            composable(route = Screens.Favorite.name) {
                val favouriteViewModel: FavouriteViewModel = hiltViewModel()
                FavouritePage(
                    uiState = favouriteViewModel.uiState,
                    getFavoritePlaygrounds = favouriteViewModel::getFavoritePlaygrounds,
                    onFavouriteClick = favouriteViewModel::onFavouriteClicked,
                    onPlaygroundClick = { playgroundId ->
                        favouriteViewModel.onClickPlayground(playgroundId)
                        navController.navigate(Screens.PlaygroundDetails.name)
                    }
                )

            }
            composable(route = Screens.MyBookings.name) {
                val bookingViewModel: MyBookingViewModel = hiltViewModel()

                MyBookingPage(
                    //uiState = bookingViewModel.uiState,
                )

            }
            composable(route = Screens.Playgrounds.name) {

            }
            composable(route = Screens.PlaygroundDetails.name) {
                val viewModel: PlaygroundViewModel = hiltViewModel()
                PlaygroundScreen(
                    playgroundStateFlow = viewModel.playgroundState,
                    playgroundUiState = viewModel.uiState,
                    onViewRatingClicked = { },
                    onClickBack = { navController.popBackStack() },
                    onClickShare = {},
                    onClickFav = { viewModel.onClickFavourite() },
                    onBookNowClicked = { },
                    onClickDisplayOnMap = {},
                    getPlaygroundDetails = viewModel::getPlaygroundDetails
                )
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
                    localUserUiState = profileViewModel.localUser,
                    onEditProfile = profileViewModel::onEditProfile,
                    onSaveProfile = profileViewModel::onSaveProfile,
                    onLogout = profileViewModel::onLogout,
                    onBackClick = { navController.popBackStack() },
                )
            }
        }
    }
}