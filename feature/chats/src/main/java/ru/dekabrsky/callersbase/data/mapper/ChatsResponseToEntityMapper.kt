package ru.dekabrsky.callersbase.data.mapper

import main.utils.orZero
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import ru.dekabrsky.callersbase.data.model.CallersBaseResponse
import ru.dekabrsky.callersbase.data.model.ChatResponse
import ru.dekabrsky.callersbase.data.model.ContentResponse
import ru.dekabrsky.callersbase.data.model.MessageResponse
import ru.dekabrsky.callersbase.data.model.UsersListIdNameResponse
import ru.dekabrsky.callersbase.domain.model.ChatEntity
import ru.dekabrsky.callersbase.domain.model.MessageEntity
import ru.dekabrsky.common.domain.model.CallersBaseEntity
import ru.dekabrsky.common.domain.model.ContactEntity
import ru.dekabrsky.feature.loginCommon.data.mapper.LoginDataMapper
import ru.dekabrsky.italks.basic.dateTime.tryParseServerDateTime
import javax.inject.Inject

class ChatsResponseToEntityMapper @Inject constructor(
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
            chatId = chat.chatId.orZero(),
            firstUser = userInfoMapper.mapUserInfo(chat.firstUser),
            secondUser = userInfoMapper.mapUserInfo(chat.secondUser),
            messages = chat.messages?.map { mapMessage(it) } ?: emptyList()
        )
    }

    fun mapMessage(msg: MessageResponse): MessageEntity {
        return MessageEntity(
            messageId = msg.messageId.orZero(),
            userId = msg.userId.orZero(),
            text = msg.text.orEmpty(),
            createdDate = msg.createdDate?.let { tryParseServerDateTime(it) } ?: LocalDateTime.now()
        )
    }

    fun mapUserForChat(response: UsersListIdNameResponse): List<ContactEntity> {
        return response.users?.map {
            ContactEntity(
                id = it.id.orZero(),
                displayName = it.displayName.orEmpty(),
                nickName = it.name.orEmpty()
            )
        }.orEmpty()
    }
}
