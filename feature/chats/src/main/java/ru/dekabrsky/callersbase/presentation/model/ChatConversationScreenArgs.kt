package ru.dekabrsky.callersbase.presentation.model

import ru.dekabrsky.login.domain.model.UserInfoEntity
import java.io.Serializable

class ChatConversationScreenArgs(
    val chatId: Int,
    val companion: UserInfoEntity
): Serializable