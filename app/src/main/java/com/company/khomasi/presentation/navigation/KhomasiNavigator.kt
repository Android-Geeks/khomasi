package com.company.khomasi.presentation.navigation

import FavouritePlaygroundsViewModel
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.company.khomasi.navigation.Screens
import com.company.khomasi.presentation.favorite.FavouritePage
import com.company.khomasi.presentation.navigation.components.BottomNavigationBar
import com.company.khomasi.presentation.ui.screens.HomeScreen
import com.company.khomasi.presentation.ui.screens.MyBookingsScreen
import com.company.khomasi.presentation.ui.screens.PlaygroundsScreen

@Composable
fun KhomasiNavigator() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.Home.name,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = Screens.Home.name) {
                HomeScreen()
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
                MyBookingsScreen()
            }
            composable(route = Screens.Playgrounds.name) {
                PlaygroundsScreen()
            }
        }
    }
}