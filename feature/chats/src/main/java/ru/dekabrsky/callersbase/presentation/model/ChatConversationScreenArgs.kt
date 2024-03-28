package ru.dekabrsky.callersbase.presentation.model

import ru.dekabrsky.feature.loginCommon.domain.model.UserInfoEntity
import java.io.Serializable

class ChatConversationScreenArgs(
    val chatId: Long,
    val companion: UserInfoEntity
): Serializable