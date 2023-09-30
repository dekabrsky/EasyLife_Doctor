package ru.dekabrsky.callersbase.presentation.model

import ru.dekabrsky.login.domain.model.UserInfoEntity
import java.io.Serializable

@Suppress("LongParameterList")
class ChatUiModel(
    val chatId: Int = 0,
    val date: String = "",
    val secondUser: UserInfoEntity,
    val lastMessage: String = "",
    val newMessagesCount: Int = 0,
    val chatIsStarted: Boolean = true
) : Serializable {
    val avatarPlaceholder
        get() = secondUser.name.firstOrNull().toString()
}