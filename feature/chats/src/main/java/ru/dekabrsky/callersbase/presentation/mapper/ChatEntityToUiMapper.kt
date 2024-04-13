package ru.dekabrsky.callersbase.presentation.mapper

import ru.dekabrsky.callersbase.domain.model.ChatEntity
import ru.dekabrsky.callersbase.presentation.model.ChatUiModel
import ru.dekabrsky.common.domain.model.CallersBaseEntity
import ru.dekabrsky.common.domain.model.ContactEntity
import ru.dekabrsky.common.presentation.model.CallersBaseUiModel
import ru.dekabrsky.feature.loginCommon.domain.model.UserInfoEntity
import ru.dekabrsky.feature.loginCommon.domain.model.UserLoginLevelEntity
import ru.dekabrsky.feature.loginCommon.domain.model.UserType
import ru.dekabrsky.italks.basic.dateTime.formatDateToUiDate
import javax.inject.Inject

class ChatEntityToUiMapper @Inject constructor() {
    fun map(entity: CallersBaseEntity): CallersBaseUiModel {
        return CallersBaseUiModel(
            date = formatDateToUiDate(entity.updated),
            count = entity.countCallers.toString(),
            title = entity.name,
            variables = entity.columns.map { "#${it}" },
            fullData = entity
        )
    }

    private fun mapChat(entity: ChatEntity): ChatUiModel {
        val lastMessage = entity.messages.maxByOrNull { it.createdDate }
        return ChatUiModel(
            chatId = entity.chatId,
            date = lastMessage?.createdDate?.let {
                MessageEntityToUiMapper.mapMessageTime(it)
            }.orEmpty(),
            secondUser = entity.secondUser,
            lastMessage = lastMessage?.text.orEmpty(),
            newMessagesCount = 0
        )
    }

    private fun mapUsersForChat(userForChatEntity: ContactEntity): ChatUiModel {
        return ChatUiModel( // todo сделать разыне модели под новый чат и сущесвтующий, и роль прибрать
            secondUser = UserInfoEntity(
                userForChatEntity.id,
                userForChatEntity.displayName,
                userForChatEntity.nickName,
                UserType.PARENT,
                currentLevel = UserLoginLevelEntity(0,0)
            ),
            chatIsStarted = false
        )
    }

    fun prepareChatsList(
        users: List<ContactEntity> = listOf(),
        chats: List<ChatEntity> = listOf()
    ): List<ChatUiModel> {
        val startedChats = chats.map { mapChat(it) }
        val startedChatsIds = startedChats.map { it.secondUser.id }
        val usersForChat = users.filter { it.id !in startedChatsIds }.map { mapUsersForChat(it) }
        return startedChats + usersForChat
    }
}