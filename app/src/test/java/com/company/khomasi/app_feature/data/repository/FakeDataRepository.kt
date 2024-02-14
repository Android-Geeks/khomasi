package com.company.khomasi.app_feature.data.repository

import com.company.khomasi.data.data_source.remote.RetrofitService
import com.company.khomasi.domain.model.AppDomainModel
import com.company.khomasi.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeDataRepository(
    private val retrofitService: RetrofitService
) : AppRepository {

}