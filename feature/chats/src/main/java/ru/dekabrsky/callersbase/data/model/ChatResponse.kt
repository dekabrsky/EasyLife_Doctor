package ru.dekabrsky.callersbase.data.model

import androidx.annotation.Keep
import ru.dekabrsky.feature.loginCommon.data.model.UserInfoResponse

@Keep
class ChatsListResponse(val chats: List<ChatResponse>)
@Keep
class ChatResponse(
    val chatId: Long?,
    val firstUser: UserInfoResponse,
    val secondUser: UserInfoResponse,
    val messages: List<MessageResponse>?
)

@Keep
class MessageResponse(
    val messageId: Long?,
    val userId: Long?,
    val chatId: Long?,
    val text: String?,
    val createdDate: String?
)