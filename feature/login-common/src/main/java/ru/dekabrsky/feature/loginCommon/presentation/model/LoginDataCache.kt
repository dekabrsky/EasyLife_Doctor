package ru.dekabrsky.feature.loginCommon.presentation.model

import ru.dekabrsky.feature.loginCommon.domain.model.UserInfoEntity
import javax.inject.Inject

class LoginDataCache @Inject constructor() {
    var currentUserData: UserInfoEntity? = null
}