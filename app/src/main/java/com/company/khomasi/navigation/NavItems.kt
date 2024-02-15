package com.company.khomasi.navigation

import com.company.khomasi.R

data class NavItem(
    val label: Int,
    val icon: Int,
    val route: String
)

val listOfNavItems = listOf(
    NavItem(
        label = R.string.home,
        icon = R.drawable.housesimple,
        route = Screens.Home.name
    ),
    NavItem(
        label = R.string.my_bookings,
        icon = R.drawable.ticket,
        route = Screens.MyBookings.name)
    ,
    NavItem(
        label = R.string.fields,
        icon = R.drawable.soccerball,
        route = Screens.Playgrounds.name
    ),
    NavItem(
        label = R.string.favorites,
        icon = R.drawable.heart,
        route = Screens.Favorite.name
    )
)