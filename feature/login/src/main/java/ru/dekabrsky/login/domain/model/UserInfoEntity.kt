package ru.dekabrsky.login.domain.model

import ru.dekabrsky.italks.tabs.domain.UserType

class UserInfoEntity(
    val id: Int,
    val name: String,
    val role: UserType
)