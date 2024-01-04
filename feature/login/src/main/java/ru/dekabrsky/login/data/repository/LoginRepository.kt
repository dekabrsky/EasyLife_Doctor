package ru.dekabrsky.login.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import ru.dekabrsky.login.data.api.LoginApi
import ru.dekabrsky.login.data.mapper.LoginDataMapperImpl
import ru.dekabrsky.feature.loginCommon.domain.model.UserInfoEntity
import ru.dekabrsky.feature.loginCommon.presentation.model.LoginDataCache
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val api: LoginApi,
    private val mapper: LoginDataMapperImpl,
    private val loginDataCache: LoginDataCache
) {
    fun login(login: String, password: String): Single<UserInfoEntity> =
        api.login(mapper.mapCredentials(login, password))
            .map { mapper.mapUserInfo(it) }
            .doOnSuccess { loginDataCache.currentUserData = it }

    fun logout(): Completable = api.logout()

    fun registration(code: String, login: String, password: String): Single<UserInfoEntity> =
        api.registration(mapper.mapRegistration(code= code, login = login, password = password))
            .map { mapper.mapUserInfo(it) }

    fun getCurrentUser(): Single<UserInfoEntity> = api.getCurrentUser().map { mapper.mapUserInfo(it) }
}