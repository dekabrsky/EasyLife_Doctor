package ru.dekabrsky.callersbase.presentation.mapper

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import ru.dekabrsky.callersbase.domain.model.ChatEntity
import ru.dekabrsky.callersbase.domain.model.MessageEntity
import ru.dekabrsky.callersbase.presentation.model.ChatMessageUiModel
import ru.dekabrsky.italks.basic.dateTime.formatDateTimeToUiDateTime
import ru.dekabrsky.italks.basic.dateTime.formatDateTimeToUiTime
import javax.inject.Inject

class MessageEntityToUiMapper @Inject constructor() {
    fun map(entity: ChatEntity): List<ChatMessageUiModel> {
        return entity.messages
            .reversed()
            .map { message ->
                val userForMessage = listOf(entity.firstUser, entity.secondUser).find { it.id == message.userId }
                ChatMessageUiModel(
                    userName = userForMessage?.name.orEmpty(),
                    isMyMessage = userForMessage?.id == entity.firstUser.id,
                    message = message.text,
                    dateTime = mapMessageTime(message.createdDate)
                )
            }
    }

    fun mapMessage(message: MessageEntity, name: String): ChatMessageUiModel {
        return ChatMessageUiModel(
            userName = name,
            isMyMessage = false,
            message = message.text,
            dateTime = mapMessageTime(message.createdDate)
        )
    }

    fun mapMyMessage(text: String): ChatMessageUiModel {
        return ChatMessageUiModel(
            userName = "Я",
            isMyMessage = true,
            message = text,
            dateTime = mapMessageTime(LocalDateTime.now())
        )
    }

    private fun mapMessageTime(date: LocalDateTime) =
        if (date.toLocalDate().equals(LocalDate.now())) {
            formatDateTimeToUiTime(date)
        } else {
            formatDateTimeToUiDateTime(date)
        }
}