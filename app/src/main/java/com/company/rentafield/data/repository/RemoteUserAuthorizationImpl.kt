package com.company.rentafield.data.repository

import com.company.rentafield.data.data_source.RetrofitService
import com.company.rentafield.domain.model.auth.UserRegisterData
import com.company.rentafield.domain.repository.RemoteUserAuthorization
import com.company.rentafield.utils.handleApi

class RemoteUserAuthorizationImpl(
    private val retrofitService: RetrofitService
) : RemoteUserAuthorization {

    override suspend fun registerUser(userRegisterData: UserRegisterData) =
        handleApi { retrofitService.registerUser(userRegisterData) }

    override suspend fun loginUser(email: String, password: String) =
        handleApi { retrofitService.loginUser(email, password) }

    override suspend fun getVerificationCode(email: String) =
        handleApi { retrofitService.getVerificationCode(email) }

    override suspend fun confirmEmail(email: String, code: String) =
        handleApi { retrofitService.confirmEmail(email, code) }

    override suspend fun recoverAccount(email: String, code: String, newPassword: String) =
        handleApi { retrofitService.recoverAccount(email, code, newPassword) }
}