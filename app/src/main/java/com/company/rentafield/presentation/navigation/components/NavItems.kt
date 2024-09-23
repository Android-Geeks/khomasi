package com.company.rentafield.presentation.navigation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.company.rentafield.R

data class NavItem(
    @StringRes val label: Int,
    @DrawableRes val icon: Int,
    val route: String
)

val listOfNavItems = listOf(
    NavItem(
        label = R.string.home,
        icon = R.drawable.housesimple,
        route = Screens.RentafieldNavigation.Home.route
    ),
    NavItem(
        label = R.string.my_bookings,
        icon = R.drawable.ticket,
        route = Screens.RentafieldNavigation.MyBookings.BookingHistory.route
    ),
    NavItem(
        label = R.string.fields,
        icon = R.drawable.soccerball,
        route = Screens.RentafieldNavigation.Playgrounds.BrowsePlaygrounds.route
    ),
    NavItem(
        label = R.string.favorites,
        icon = R.drawable.heart,
        route = Screens.RentafieldNavigation.Favorite.route
    )
)