package ru.dekabrsky.callersbase.data.model

import androidx.annotation.Keep

@Keep
class UsersListIdNameResponse(
    val users: List<UserIdNameResponse>?
)

@Keep
class UserIdNameResponse(
    val id: Int?,
    val name: String?
)