package com.company.khomasi.presentation.myBookings.components

import androidx.compose.runtime.Composable
import com.company.khomasi.R

//sealed class TabItem(
//    val title: String,
//    val screens: @Composable (
//        uiState: StateFlow<MyBookingUiState>, playgroundPicture: StateFlow<DataState<PlaygroundPicture>>
//
//    ) -> Unit
//) {
//    object Current : TabItem(
//        title = "${R.string.current}",
//        screens = { uiState, playgroundPicture -> CurrentPage(uiState) })
//
//    object Expired : TabItem(
//        title = "${R.string.expired}",
//        screens = { uiState, playgroundPicture -> ExpiredPage(uiState) })
//}
typealias ComposableFun = @Composable ()->Unit

sealed class TabItem(val title:String, val screens: ComposableFun){

    data object Current : TabItem(title = "${R.string.current}", screens = { CurrentPage() })
    data object Expired: TabItem(title = "${R.string.expired}", screens={ ExpiredPage() })


}