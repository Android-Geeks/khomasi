package com.company.rentafield.app_feature.data.repository

import com.company.rentafield.data.data_source.RetrofitService
import com.company.rentafield.domain.repository.AppRepository

class FakeDataRepository(
    private val retrofitService: RetrofitService
) : AppRepository