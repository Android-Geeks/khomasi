package com.company.khomasi.presentation.favorite

data class FavouriteUiState(
    val name: String=" ",
    val address: String=" ",
    val imageUrl: String=" ",
    val rating: Float=0.0f,
    val price: String=" ",
    val openingHours: String=" ",
    val isFavorite: Boolean=false,
    val isBookable: Boolean=false,
    val userId:String=" ",
    val playgroundId:String=" ",
)
