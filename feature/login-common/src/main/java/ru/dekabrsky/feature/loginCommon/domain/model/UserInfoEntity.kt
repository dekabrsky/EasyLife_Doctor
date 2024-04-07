package ru.dekabrsky.feature.loginCommon.domain.model

import java.io.Serializable

class UserInfoEntity(
    val id: Long,
    val name: String,
    val displayName: String,
    val role: UserType,
    val currentLevel: UserLoginLevelEntity
): Serializable {
    val nickname
        get() = name.takeIf { it.isNotEmpty() }?.let { "@$it" }.orEmpty()

    val nameWithNickname
        get() = listOf(displayName, nickname).joinToString(" ")
}

class UserLoginLevelEntity(
    val score: Int,
    val experience: Int
)