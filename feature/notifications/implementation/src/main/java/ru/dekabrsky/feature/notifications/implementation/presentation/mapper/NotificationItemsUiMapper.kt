package ru.dekabrsky.feature.notifications.implementation.presentation.mapper

import ru.dekabrsky.feature.notifications.implementation.presentation.model.NotificationItemUiModel
import javax.inject.Inject

class NotificationItemsUiMapper @Inject constructor() {
    fun map() = listOf(
        NotificationItemUiModel(
            id = 1,
            time = "11:00",
            name = "Голодоутолин",
            dosage = "1 тарелка",
            note = "Во время еды",
            enabled = true
        ),
        NotificationItemUiModel(
            id = 2,
            time = "12:30",
            name = "Препарат V",
            dosage = "2 инъекции",
            note = "По необходимости",
            enabled = true
        ),
        NotificationItemUiModel(
            id = 3,
            time = "18:00",
            name = "Спутник Лайт",
            dosage = "5 баллов",
            note = "Во время еды",
            enabled = false
        ),
        NotificationItemUiModel(
            id = 4,
            time = "19:30",
            name = "Препарат V",
            dosage = "2 инъекции",
            note = "По необходимости",
            enabled = true
        )
    )
}