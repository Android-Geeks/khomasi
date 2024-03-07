package com.company.khomasi.domain.use_case.location

import com.company.khomasi.domain.repository.LocationRepository

class GetLastUserLocation(
    private val locationRepository: LocationRepository
) {
    operator fun invoke(
        onGetLastLocationSuccess: (Pair<Double, Double>) -> Unit,
        onGetLastLocationFailed: (Exception) -> Unit,
        onGetLastLocationIsNull: () -> Unit
    ) = locationRepository.getLastUserLocation(
        onGetLastLocationSuccess = onGetLastLocationSuccess,
        onGetLastLocationFailed = onGetLastLocationFailed,
        onGetLastLocationIsNull = onGetLastLocationIsNull
    )
}