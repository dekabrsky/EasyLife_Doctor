package ru.dekabrsky.login.domain.model

import ru.dekabrsky.italks.tabs.domain.UserType
import ru.dekabrsky.login.data.model.LoginLevelResponse
import java.io.Serializable

class UserInfoEntity(
    val id: Int,
    val name: String,
    val role: UserType,
    val currentLevel: UserLoginLevelEntity
): Serializable

class UserLoginLevelEntity(
    val score: Int,
    val experience: Int
)