package ru.dekabrsky.callersbase.domain.entity

import org.threeten.bp.LocalDateTime
import ru.dekabrsky.login.data.model.UserInfoResponse
import ru.dekabrsky.login.domain.model.UserInfoEntity

class ChatEntity(
    val chatId: Int,
    val firstUser: UserInfoEntity,
    val secondUser: UserInfoEntity,
    val messages: List<MessageEntity>
)

class MessageEntity(
    val messageId: Int,
    val userId: Int,
    val text: String,
    val createdDate: LocalDateTime
)