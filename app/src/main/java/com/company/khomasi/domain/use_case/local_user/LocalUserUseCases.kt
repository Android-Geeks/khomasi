package com.company.khomasi.domain.use_case.local_user

data class LocalUserUseCases(
    val saveLocalUser: SaveLocalUser,
    val getLocalUser: GetLocalUser,
    val saveSearchHistory: SaveSearchHistory,
    val getSearchHistory: GetSearchHistory,
    val removeSearchHistory: RemoveSearchHistory
)