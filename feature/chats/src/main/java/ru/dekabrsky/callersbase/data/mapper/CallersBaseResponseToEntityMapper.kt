package ru.dekabrsky.callersbase.data.mapper

import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import ru.dekabrsky.callersbase.data.model.CallersBaseResponse
import ru.dekabrsky.callersbase.data.model.ChatResponse
import ru.dekabrsky.callersbase.data.model.ContentResponse
import ru.dekabrsky.callersbase.data.model.MessageResponse
import ru.dekabrsky.callersbase.domain.entity.ChatEntity
import ru.dekabrsky.callersbase.domain.entity.MessageEntity
import ru.dekabrsky.common.domain.model.CallersBaseEntity
import ru.dekabrsky.italks.basic.dateTime.formatServerDateToLocalDateTime
import ru.dekabrsky.login.data.mapper.LoginDataMapper
import javax.inject.Inject

class CallersBaseResponseToEntityMapper @Inject constructor(
    private val loginDataMapper: LoginDataMapper
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
            chatId = chat.chatId,
            firstUser = loginDataMapper.mapUserInfo(chat.firstUser),
            secondUser = loginDataMapper.mapUserInfo(chat.secondUser),
            messages = chat.messages.map { mapMessages(it) }
        )
    }

    private fun mapMessages(message: MessageResponse): MessageEntity {
        return MessageEntity(
            messageId = message.messageId,
            userId = message.userId,
            text = message.text,
            createdDate = formatServerDateToLocalDateTime(message.createdDate)
        )
    }
}
