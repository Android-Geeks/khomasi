package com.company.khomasi.presentation.favorite

data class FavUiState(
    val name: String,
    val address: String,
    val imageUrl: String,
    val rating: Float,
    val price: String,
    val openingHours: String,
    val isFavorite: Boolean,
    val isBookable: Boolean
)
