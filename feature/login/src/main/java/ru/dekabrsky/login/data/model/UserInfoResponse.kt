package ru.dekabrsky.login.data.model

import androidx.annotation.Keep

@Keep
class UserInfoResponse(
    val id: Int? = 0,
    val name: String? = "",
    val roleName: String? = "",
    val currentLevel: LoginLevelResponse?
)

@Keep
class LoginLevelResponse (
    val score: Int?,
    val experience: Int?
)