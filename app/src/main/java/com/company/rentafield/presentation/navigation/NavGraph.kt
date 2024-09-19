package com.company.rentafield.presentation.navigation

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.company.rentafield.navigation.Screens
import com.company.rentafield.navigation.listOfNavItems
import com.company.rentafield.presentation.components.connectionStates.LossConnection
import com.company.rentafield.presentation.navigation.components.BottomNavigationBar
import com.company.rentafield.presentation.navigation.navigators.authNavigator
import com.company.rentafield.presentation.navigation.navigators.onboardingNavigator
import com.company.rentafield.presentation.navigation.navigators.rentAfieldNavigator
import com.company.rentafield.theme.RentafieldTheme
import com.company.rentafield.utils.ConnectivityObserver

@Composable
fun NavGraph(
    startDestination: String,
    isNetworkAvailable: ConnectivityObserver.Status
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    if (isNetworkAvailable == ConnectivityObserver.Status.Unavailable ||
        isNetworkAvailable == ConnectivityObserver.Status.Lost
    ) {
        LossConnection()
        return
    }

    Scaffold(
        bottomBar =
        {
            if (navBackStackEntry?.destination?.route in listOfNavItems.map { it.route }) {
                BottomNavigationBar(
                    navController = navController,
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.then(
                if (navBackStackEntry?.destination?.route in listOfNavItems.map { it.route })
                    Modifier.padding(bottom = paddingValues.calculateBottomPadding())
                else Modifier
            )
        ) {
            // Navigation for onboarding
            onboardingNavigator()

            // Navigation for Auth
            authNavigator(navController)

            // Navigation for RentAfield app
            rentAfieldNavigator(navController)
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    RentafieldTheme {
        NavGraph(
            startDestination = Screens.RentafieldNavigation.route,
            isNetworkAvailable = ConnectivityObserver.Status.Unavailable
        )
    }
}