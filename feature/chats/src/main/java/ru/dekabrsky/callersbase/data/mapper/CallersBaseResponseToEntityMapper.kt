package ru.dekabrsky.callersbase.data.mapper

import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import ru.dekabrsky.callersbase.data.model.CallersBaseResponse
import ru.dekabrsky.callersbase.data.model.ChatResponse
import ru.dekabrsky.callersbase.data.model.ContentResponse
import ru.dekabrsky.callersbase.data.model.MessageResponse
import ru.dekabrsky.callersbase.data.model.UsersListIdNameResponse
import ru.dekabrsky.callersbase.domain.model.ChatEntity
import ru.dekabrsky.callersbase.domain.model.MessageEntity
import ru.dekabrsky.callersbase.domain.model.UserForChatEntity
import ru.dekabrsky.common.domain.model.CallersBaseEntity
import ru.dekabrsky.login.data.mapper.LoginDataMapper
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class CallersBaseResponseToEntityMapper @Inject constructor(
    private val userInfoMapper: LoginDataMapper
) {
    fun mapList(response: CallersBaseResponse): List<CallersBaseEntity> {
        return response.content.map { base -> mapBase(base) }
    }

    fun mapBase(base: ContentResponse) =
        CallersBaseEntity(
            id = base.id,
            columns = base.columns.map { it.currentName },
            confirmed = base.confirmed,
            countCallers = base.countCallers,
            countInvalidCallers = base.countInvalidCallers,
            name = base.name,
            updated = Instant.ofEpochMilli(base.updated)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
        )

    fun mapChat(chat: ChatResponse): ChatEntity {
        return ChatEntity(
            chatId = chat.chatId ?: 0,
            firstUser = userInfoMapper.mapUserInfo(chat.firstUser),
            secondUser = userInfoMapper.mapUserInfo(chat.secondUser),
            messages = chat.messages?.map { mapMessage(it) } ?: emptyList()
        )
    }

    fun mapMessage(msg: MessageResponse): MessageEntity {
        return MessageEntity(
            messageId = msg.messageId ?: 0,
            userId = msg.userId ?: 0,
            text = msg.text.orEmpty(),
            createdDate = LocalDateTime.parse(msg.createdDate)
        )
    }

    fun mapUserForChat(response: UsersListIdNameResponse): List<UserForChatEntity> {
        return response.users?.map { UserForChatEntity(it.id ?: 0, it.name.orEmpty()) }.orEmpty()
    }
}
