package ru.dekabrsky.feature.loginCommon.presentation.model

import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class TokenCache @Inject constructor() {
    var accessToken: String? = null
        set(value) {
            field = value
            accessTokenReceived.onNext(Unit)
        }
    var expiresIn: Int? = null
    var refreshToken: String? = null

    var accessTokenReceived = PublishSubject.create<Unit>()
}