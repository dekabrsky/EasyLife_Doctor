package ru.dekabrsky.feature.loginCommon.domain.interactor

import io.reactivex.Completable
import io.reactivex.Single

interface LoginInteractor {
    fun logout(deviceToken: String): Completable
    fun getFcmToken(): Single<String>
}