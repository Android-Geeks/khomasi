package com.company.khomasi.navigation

import com.company.khomasi.R

data class NavItem(
    val label: String,
    val icon: Int,
    val route: String
)

val listOfNavItems = listOf(
    NavItem(
        label = "Home",
        icon = R.drawable.housesimple,
        route = Screens.Home.name
    ),
    NavItem(
        label = "Reservations",
        icon = R.drawable.ticket,
        route = Screens.MyReservations.name)
    ,
    NavItem(
        label = "Playgrounds",
        icon = R.drawable.soccerball,
        route = Screens.Playgrounds.name
    ),
    NavItem(
        label = "Favorite",
        icon = R.drawable.heart,
        route = Screens.Favorite.name
    )
)