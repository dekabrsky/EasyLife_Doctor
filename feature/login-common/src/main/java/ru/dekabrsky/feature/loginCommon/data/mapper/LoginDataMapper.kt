package ru.dekabrsky.feature.loginCommon.data.mapper

import ru.dekabrsky.feature.loginCommon.data.model.UserInfoResponse
import ru.dekabrsky.feature.loginCommon.domain.model.UserInfoEntity

interface LoginDataMapper {
    fun mapUserInfo(response: UserInfoResponse): UserInfoEntity
}