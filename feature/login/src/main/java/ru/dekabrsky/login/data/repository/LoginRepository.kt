package ru.dekabrsky.login.data.repository

import io.reactivex.Single
import ru.dekabrsky.login.data.api.LoginApi
import ru.dekabrsky.login.data.mapper.LoginDataMapper
import ru.dekabrsky.login.data.model.UserInfoResponse
import ru.dekabrsky.login.domain.model.UserInfoEntity
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val api: LoginApi,
    private val mapper: LoginDataMapper
) { // тут решил пропустить domain-слой
    fun login(login: String, password: String): Single<UserInfoEntity> =
        api.login(mapper.mapCredentials(login, password))
            .map { mapper.mapUserInfo(it) }

    //fun getCurrentUser(): Single<UserInfoResponse> = api.getCurrentUser()
}