package ru.dekabrsky.feature.loginCommon.data.model

import androidx.annotation.Keep

@Keep
class UserInfoResponse(
    val id: Long?,
    val name: String?,
    val displayName: String?,
    val roleId: Int?,
    val currentLevel: LoginLevelResponse?
)

@Keep
class LoginLevelResponse (
    val score: Int?,
    val experience: Int?
)