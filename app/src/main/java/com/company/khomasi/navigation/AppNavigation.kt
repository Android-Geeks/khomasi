package com.company.khomasi.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.company.khomasi.presentation.ui.screens.FavoriteScreen
import com.company.khomasi.presentation.ui.screens.HomeScreen
import com.company.khomasi.presentation.ui.screens.MyBookingsScreen
import com.company.khomasi.presentation.ui.screens.PlaygroundsScreen
import com.company.khomasi.theme.KhomasiTheme

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    Scaffold (
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                listOfNavItems.forEach{ navItem ->
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any{it.route == navItem.route} == true,
                    onClick = {
                              navController.navigate(navItem.route){
                                  popUpTo(navController.graph.findStartDestination().id){
                                      saveState = true
                                  }
                                  launchSingleTop = true
                                  restoreState = true
                              }

                              },
                    icon = {
                        Icon(
                            painter = painterResource(id = navItem.icon),
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = stringResource(id = navItem.label))
                    }
                )
                }

            }

        }
    ){paddingValues ->

        NavHost(
            navController = navController,
            startDestination = Screens.Home.name,
            modifier = Modifier.padding(paddingValues)
            ){
            composable(route = Screens.Home.name){
                HomeScreen()
            }
            composable(route = Screens.Favorite.name){
                FavoriteScreen()
            }
            composable(route = Screens.MyBookings.name){
                MyBookingsScreen()
            }
            composable(route = Screens.Playgrounds.name){
                PlaygroundsScreen()
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AppNavigationPreview(){
    KhomasiTheme {
        AppNavigation()
    }
}