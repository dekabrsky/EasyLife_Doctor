package ru.dekabrsky.feature.loginCommon.domain.interactor

import io.reactivex.Completable

interface LoginInteractor {
    fun logout(): Completable
}