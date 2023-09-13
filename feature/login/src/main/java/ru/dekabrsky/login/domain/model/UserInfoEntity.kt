package ru.dekabrsky.login.domain.model

import ru.dekabrsky.italks.tabs.domain.UserType
import java.io.Serializable

class UserInfoEntity(
    val id: Int,
    val name: String,
    val role: UserType
): Serializable