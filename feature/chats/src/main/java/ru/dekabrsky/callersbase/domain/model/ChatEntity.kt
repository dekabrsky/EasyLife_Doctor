package ru.dekabrsky.callersbase.domain.model

import org.threeten.bp.LocalDateTime
import ru.dekabrsky.login.data.model.UserInfoResponse
import ru.dekabrsky.login.domain.model.UserInfoEntity
import java.time.LocalDate

class ChatEntity(
    val chatId: Int,
    val firstUser: UserInfoEntity,
    val secondUser: UserInfoEntity,
    val messages: List<MessageEntity>
)

class MessageEntity(
    val messageId: Int = 0,
    val userId: Int = 0,
    val text: String = "",
    val createdDate: LocalDateTime = LocalDateTime.now()
)