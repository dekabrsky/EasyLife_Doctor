package ru.dekabrsky.callersbase.presentation.mapper

import ru.dekabrsky.callersbase.domain.model.ChatEntity
import ru.dekabrsky.callersbase.domain.model.MessageEntity
import ru.dekabrsky.common.domain.model.ContactEntity
import ru.dekabrsky.common.domain.model.CallersBaseEntity
import ru.dekabrsky.common.presentation.model.CallersBaseUiModel
import ru.dekabrsky.callersbase.presentation.model.ChatUiModel
import ru.dekabrsky.italks.basic.dateTime.formatDateTimeToUiDateTime
import ru.dekabrsky.italks.basic.dateTime.formatDateToUiDate
import ru.dekabrsky.feature.loginCommon.domain.model.UserType
import ru.dekabrsky.feature.loginCommon.domain.model.UserInfoEntity
import ru.dekabrsky.feature.loginCommon.domain.model.UserLoginLevelEntity
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
        val lastMessage = entity.messages.maxByOrNull { it.createdDate } ?: MessageEntity()
        return ChatUiModel(
            chatId = entity.chatId,
            date = entity.messages.maxOfOrNull { it.createdDate }?.let { formatDateTimeToUiDateTime(it) }.orEmpty(),
            secondUser = entity.secondUser,
            lastMessage = lastMessage.text,
            newMessagesCount = 0
        )
    }

    private fun mapUsersForChat(userForChatEntity: ContactEntity): ChatUiModel {
        return ChatUiModel( // todo сделать разыне модели под новый чат и сущесвтующий, и роль прибрать
            secondUser = UserInfoEntity(userForChatEntity.id, userForChatEntity.name, UserType.PARENT,
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

//    fun mapDoctorChats(): List<ChatUiModel> {
//        return listOf(
//            ChatUiModel("10:12", "Пациент #0373", "Посмотрите пожалуйста вот эту штуку", 1),
//            ChatUiModel("15 дек", "Родитель #0373", "Посмотрите пожалуйста вот эту штуку", 0),
//            ChatUiModel("18 дек", "Техническая поддержка", "Вы: А если запустить с веба?", 0),
//            ChatUiModel("18 дек", "Даниил Редкозубов", "Вы: Система работает. Тестируем", 0),
//            ChatUiModel("11 дек", "Пациент #0356", "Хорошо, приедем во вторник", 0),
//        )
//    }

//    fun mapParentChats(): List<ChatUiModel> {
//        return listOf(
//            ChatUiModel("10:12", "Наталья Иванова", "Посмотрите пожалуйста вот эту штуку", 1),
//            ChatUiModel("18 дек", "Техническая поддержка", "Тестовое сообщение", 0),
//            ChatUiModel("18 дек", "Вячеслав Васильев", "Вы: Отправил результаты анализов", 0),
//        )
//    }
}