package com.company.rentafield.app_feature.data.repository

import com.company.rentafield.data.datasource.RetrofitService
import com.company.rentafield.domain.repository.AppRepository

class FakeDataRepository(
    private val retrofitService: RetrofitService
) : AppRepository