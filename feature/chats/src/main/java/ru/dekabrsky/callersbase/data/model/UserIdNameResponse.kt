package ru.dekabrsky.callersbase.data.model

import androidx.annotation.Keep

@Keep
class UsersListIdNameResponse(
    val users: List<UserIdNameResponse>?
)

@Keep
class UserIdNameResponse(
    val id: Long?,
    val name: String?
)