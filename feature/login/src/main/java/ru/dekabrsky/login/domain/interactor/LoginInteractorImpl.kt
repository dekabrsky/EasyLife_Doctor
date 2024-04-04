package ru.dekabrsky.login.domain.interactor

import io.reactivex.Completable
import io.reactivex.Single
import ru.dekabrsky.feature.loginCommon.domain.interactor.LoginInteractor
import ru.dekabrsky.login.data.repository.LoginRepository
import ru.dekabrsky.feature.loginCommon.domain.model.UserInfoEntity
import javax.inject.Inject

class LoginInteractorImpl @Inject constructor(
    private val repository: LoginRepository
): LoginInteractor {

    fun login(login: String, password: String, token: String): Single<UserInfoEntity> =
       repository.login(login, password, token)

    override fun logout(deviceToken: String): Completable = repository.logout(deviceToken)

    fun registration(code: String, login: String, password: String): Single<UserInfoEntity> =
       repository.registration(code, login, password)

    fun getCurrentUser(): Single<UserInfoEntity> = repository.getCurrentUser()

    override fun getFcmToken(): Single<String> = repository.getFcmToken()
}