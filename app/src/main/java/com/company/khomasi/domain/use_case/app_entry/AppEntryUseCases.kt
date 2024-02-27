package com.company.khomasi.domain.use_case.app_entry

data class AppEntryUseCases(
    val readAppEntry: ReadAppEntry,
    val saveAppEntry: SaveAppEntry,
    val saveIsLogin: SaveIsLogin
)