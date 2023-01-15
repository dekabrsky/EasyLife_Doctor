package ru.dekabrsky.callersbase.presentation.mapper

import ru.dekabrsky.common.domain.model.CallersBaseEntity
import ru.dekabrsky.common.presentation.model.CallersBaseUiModel
import ru.dekabrsky.common.presentation.model.ChatUiModel
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

    fun mapDoctorChats(): List<ChatUiModel> {
        return listOf(
            ChatUiModel("10:12", "Пациент #0373", "Посмотрите пожалуйста вот эту штуку", 1),
            ChatUiModel("18 дек", "Техническая поддержка", "Вы: А если запустить с веба?", 0),
            ChatUiModel("18 дек", "Даниил Редкозубов", "Вы: Система работает. Тестируем", 0),
            ChatUiModel("11 дек", "Пациент #0356", "Хорошо, приедем во вторник", 0),
        )
    }

    fun mapParentChats(): List<ChatUiModel> {
        return listOf(
            ChatUiModel("10:12", "Наталья Иванова", "Посмотрите пожалуйста вот эту штуку", 1),
            ChatUiModel("18 дек", "Техническая поддержка", "Тестовое сообщение", 0),
            ChatUiModel("18 дек", "Вячеслав Васильев", "Вы: Отправил результаты анализов", 0),
        )
    }
}