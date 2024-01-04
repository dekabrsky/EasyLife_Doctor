package ru.dekabrsky.feature.loginCommon.domain.model

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