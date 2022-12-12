package ru.dekabrsky.callersbase.presentation.mapper

import ru.dekabrsky.callersbase_common.domain.model.CallersBaseEntity
import ru.dekabrsky.callersbase_common.presentation.model.CallersBaseUiModel
import ru.dekabrsky.callersbase_common.presentation.model.ChatUiModel
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

    fun mapChats(): List<ChatUiModel> {
        return listOf(
            ChatUiModel("10:12", "Пациент #0373", "Посмотрите пожалуйста вот эту штуку", 1),
            ChatUiModel("11 дек", "Пациент #0356", "Хорошо, приедем во вторник", 0),
            ChatUiModel("11 дек", "Наталья Савченко", "Вы: Система работает. Тестируем", 0),
            ChatUiModel("7 дек", "Пациент #0152", "Вы: А если запустить с веба?", 0),
        )
    }
}