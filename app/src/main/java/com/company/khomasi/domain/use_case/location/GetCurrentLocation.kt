package com.company.khomasi.domain.use_case.location

import com.company.khomasi.domain.repository.LocationRepository

class GetCurrentLocation(
    private val locationRepository: LocationRepository
) {
    operator fun invoke(
        onGetCurrentLocationSuccess: (Pair<Double, Double>) -> Unit,
        onGetCurrentLocationFailed: (Exception) -> Unit,
        priority: Boolean
    ) = locationRepository.getCurrentLocation(
        onGetCurrentLocationSuccess = onGetCurrentLocationSuccess,
        onGetCurrentLocationFailed = onGetCurrentLocationFailed,
        priority = priority
    )
}