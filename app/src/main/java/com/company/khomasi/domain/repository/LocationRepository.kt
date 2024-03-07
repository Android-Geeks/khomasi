package com.company.khomasi.domain.repository

interface LocationRepository {
    fun getCurrentLocation(
        onGetCurrentLocationSuccess: (Pair<Double, Double>) -> Unit,
        onGetCurrentLocationFailed: (Exception) -> Unit,
        priority: Boolean
    )

    fun getLastUserLocation(
        onGetLastLocationSuccess: (Pair<Double, Double>) -> Unit,
        onGetLastLocationFailed: (java.lang.Exception) -> Unit,
        onGetLastLocationIsNull: () -> Unit
    )
}