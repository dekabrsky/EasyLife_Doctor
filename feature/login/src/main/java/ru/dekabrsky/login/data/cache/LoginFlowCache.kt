package ru.dekabrsky.login.data.cache

import ru.dekabrsky.login.domain.model.UserInfoEntity
import ru.dekabrsky.login.domain.model.UserLoginLevelEntity
import javax.inject.Inject

class LoginFlowCache @Inject constructor() {
    var infoUser: List<UserInfoEntity> = listOf()
    var infoLevelUser: UserLoginLevelEntity? = null
}