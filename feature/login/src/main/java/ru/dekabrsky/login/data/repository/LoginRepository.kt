package ru.dekabrsky.login.data.repository

import com.google.firebase.messaging.FirebaseMessaging
import io.reactivex.Completable
import io.reactivex.Single
import ru.dekabrsky.feature.loginCommon.domain.model.UserInfoEntity
import ru.dekabrsky.feature.loginCommon.presentation.model.LoginDataCache
import ru.dekabrsky.feature.loginCommon.presentation.model.TokenCache
import ru.dekabrsky.login.data.api.LoginApi
import ru.dekabrsky.login.data.mapper.LoginDataMapperImpl
import ru.dekabrsky.login.data.model.RefreshRequest
import ru.dekabrsky.login.domain.entity.LoginTokenEntity
import ru.dekabrsky.sharedpreferences.SharedPreferencesProvider
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val api: LoginApi,
    private val mapper: LoginDataMapperImpl,
    private val loginDataCache: LoginDataCache,
    private val tokenCache: TokenCache,
    private val sharedPreferencesProvider: SharedPreferencesProvider
) {
    fun login(login: String, password: String, token: String): Single<LoginTokenEntity> =
        api.login(mapper.mapCredentials(login, password, token))
            .map { mapper.mapToken(it) }
            .doOnSuccess(::updateTokens)

    fun logout(deviceToken: String): Completable = api.logout(mapper.mapLogout(deviceToken))

    fun registration(code: String, login: String, password: String): Single<LoginTokenEntity> =
        api.registration(mapper.mapRegistration(code = code, login = login, password = password))
            .map { mapper.mapToken(it) }
            .doOnSuccess(::updateTokens)

    fun refresh(refreshToken: String): Single<LoginTokenEntity> =
        api.refresh(RefreshRequest(refreshToken))
            .map { mapper.mapToken(it) }
            .doOnSuccess(::updateTokens)

    fun getCurrentUser(): Single<UserInfoEntity> =
        api.getCurrentUser()
            .map { mapper.mapUserInfo(it) }
            .doOnSuccess { loginDataCache.currentUserData = it }

    fun getFcmToken() = Single.create { emitter ->
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                emitter.onSuccess(task.result)
            } else {
                emitter.onError(Exception())
            }
        }
    }.onErrorReturn { "" }

    private fun updateTokens(it: LoginTokenEntity) {
        tokenCache.expiresIn = it.expiresIn
        tokenCache.refreshToken = it.refreshToken
        tokenCache.accessToken = it.accessToken
    }
}