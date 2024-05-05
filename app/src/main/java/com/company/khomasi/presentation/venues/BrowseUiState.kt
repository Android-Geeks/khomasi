package com.company.khomasi.presentation.venues

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.company.khomasi.domain.model.Playground
import java.time.LocalDateTime


data class BrowseUiState(
    val price: Int = 50,
    val maxValue: Float = 500f,
    val type: Int = 5,
    val bookingTime: String = LocalDateTime.now().plusDays(1).toString(),
    val duration: Double = 1.0,
    val selectedDuration: Int = 60,
    val selectedFilter: SelectedFilter = SelectedFilter.Rating,
    val playgrounds: List<Playground> = listOf(),
    val playgroundsResult: List<Playground> = listOf(),
    val choice: MutableIntState = mutableIntStateOf(0),
    val listOfTypes: List<Int> = listOf(5, 6, 8, 11),
    val selectedType: SnapshotStateList<Int> = mutableStateListOf(5)

)

enum class SelectedFilter {
    Rating,
    Nearest,
    Bookable
}