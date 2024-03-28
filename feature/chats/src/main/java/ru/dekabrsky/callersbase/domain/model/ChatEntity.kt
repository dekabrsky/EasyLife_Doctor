package ru.dekabrsky.callersbase.domain.model

import org.threeten.bp.LocalDateTime
import ru.dekabrsky.feature.loginCommon.domain.model.UserInfoEntity

class ChatEntity(
    val chatId: Long,
    val firstUser: UserInfoEntity,
    val secondUser: UserInfoEntity,
    val messages: List<MessageEntity>
)

class MessageEntity(
    val messageId: Long = 0,
    val userId: Long = 0,
    val text: String = "",
    val createdDate: LocalDateTime = LocalDateTime.now()
)