package com.company.rentafield.presentation.screens.venues

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import java.time.LocalDateTime


data class BrowseUiState(
    val price: Int = 50,
    val maxValue: Float = 500f,
    val type: Int = 5,
    val bookingTime: String = LocalDateTime.now().plusDays(1).toString(),
    val duration: Double = 1.0,
    val selectedDuration: Int = 60,
    val selectedFilter: SelectedFilter = SelectedFilter.Rating,
    val playgrounds: List<com.company.rentafield.domain.models.playground.Playground> = listOf(),
    val playgroundsResult: List<com.company.rentafield.domain.models.playground.Playground> = listOf(),
    val choice: MutableIntState = mutableIntStateOf(0),
    val listOfTypes: List<Int> = listOf(5, 6, 8, 11),
    val selectedType: SnapshotStateList<Int> = mutableStateListOf(5)

)

enum class SelectedFilter {
    Rating,
    Nearest,
    Bookable
}