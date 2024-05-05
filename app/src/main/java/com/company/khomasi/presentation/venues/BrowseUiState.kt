package com.company.khomasi.presentation.venues

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.company.khomasi.domain.model.Playground


data class BrowseUiState(
    val price: Int = 50,
    val maxValue: Float = 500f,
    val type: Int = 5,
    val bookingTime: String = "2024-10-01T09:00:00Z",
    val duration: Double = 1.0,
    val selectedDuration: Int = 60,
    val selectedFilter: SelectedFilter = SelectedFilter.Rating,
    val playgrounds: List<Playground> = listOf(),
    val playgroundsResult: List<Playground> = listOf(),
    val choice: MutableIntState = mutableIntStateOf(0),
    val listOfTypes: List<Int> = listOf(5, 8, 11),
    val selectedType: SnapshotStateList<Int> = mutableStateListOf(5)

)

enum class SelectedFilter {
    Rating,
    Nearest,
    Bookable
}