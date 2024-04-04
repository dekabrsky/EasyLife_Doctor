package ru.dekabrsky.login.data.repository

import com.google.firebase.messaging.FirebaseMessaging
import io.reactivex.Completable
import io.reactivex.Single
import ru.dekabrsky.login.data.api.LoginApi
import ru.dekabrsky.login.data.mapper.LoginDataMapperImpl
import ru.dekabrsky.feature.loginCommon.domain.model.UserInfoEntity
import ru.dekabrsky.feature.loginCommon.presentation.model.LoginDataCache
import ru.dekabrsky.login.data.model.LogoutRequest
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val api: LoginApi,
    private val mapper: LoginDataMapperImpl,
    private val loginDataCache: LoginDataCache
) {
    fun login(login: String, password: String, token: String): Single<UserInfoEntity> =
        api.login(mapper.mapCredentials(login, password, token))
            .map { mapper.mapUserInfo(it) }
            .doOnSuccess { loginDataCache.currentUserData = it }

    fun logout(deviceToken: String): Completable = api.logout(mapper.mapLogout(deviceToken))

    fun registration(code: String, login: String, password: String): Single<UserInfoEntity> =
        api.registration(mapper.mapRegistration(code= code, login = login, password = password))
            .map { mapper.mapUserInfo(it) }

    fun getCurrentUser(): Single<UserInfoEntity> = api.getCurrentUser().map { mapper.mapUserInfo(it) }

    fun getFcmToken() = Single.create { emitter ->
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                emitter.onSuccess(task.result)
            } else {
                emitter.onError(Exception())
            }
        }
    }.onErrorReturn { "" }
}