package com.company.khomasi.data.repository

import com.company.khomasi.data.data_source.local.database.AppDao
import com.company.khomasi.data.data_source.remote.RetrofitService
import com.company.khomasi.domain.repository.AppRepository


class AppRepositoryImpl(
    private val dao: AppDao,
    private val retrofitService: RetrofitService,
) : AppRepository {


}