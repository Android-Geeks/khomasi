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
import com.company.khomasi.presentation.navigation.components.BottomNavigationBar
import com.company.khomasi.presentation.search.SearchScreen
import com.company.khomasi.presentation.search.SearchViewModel
import com.company.khomasi.presentation.ui.screens.FavoriteScreen
import com.company.khomasi.presentation.ui.screens.HomeScreen
import com.company.khomasi.presentation.ui.screens.MyBookingsScreen
import com.company.khomasi.presentation.ui.screens.PlaygroundsScreen

@Composable
fun KhomasiNavigator() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = if (navController.currentDestination?.route in listOfNavItems.map { it.route }) {
            { BottomNavigationBar(navController) }
        } else {
            {}
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.Search.name,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = Screens.Home.name) {
                HomeScreen()
            }
            composable(route = Screens.Favorite.name) {
                FavoriteScreen()
            }
            composable(route = Screens.MyBookings.name) {
                MyBookingsScreen()
            }
            composable(route = Screens.Playgrounds.name) {
                PlaygroundsScreen()
            }
            composable(route = Screens.Search.name) {
                val searchViewModel: SearchViewModel = hiltViewModel()
                SearchScreen(
                    onBackClick = { navController.popBackStack() },
                    uiState = searchViewModel.uiState.collectAsState().value,
                    onQueryChange = searchViewModel::onSearchQueryChanged,
                    onSearchQuerySubmitted = searchViewModel::onSearchQuerySubmitted,
                    onSearchFilterChanged = searchViewModel::onSearchFilterChanged,
                    onClearHistory = searchViewModel::onClickRemoveSearchHistory
                )
            }
        }
    }
}