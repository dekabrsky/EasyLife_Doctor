package ru.dekabrsky.feature.notifications.implementation.presentation.mapper

import ru.dekabrsky.feature.notifications.implementation.domain.entity.NotificationEntity
import ru.dekabrsky.feature.notifications.implementation.presentation.model.NotificationEditUiModel
import javax.inject.Inject

class NotificationEntityToUiMapper @Inject constructor() {
    fun mapUiToEntity(uiModel: NotificationEditUiModel, id: Long? = null) =
        NotificationEntity(
            uid = id,
            tabletName = uiModel.tabletName,
            dosage = uiModel.dosage,
            note = uiModel.note,
            hour = uiModel.hour,
            minute = uiModel.minute
        )

    fun mapEntityToUi(it: NotificationEntity) =
        NotificationEditUiModel(
            tabletName = it.tabletName,
            dosage = it.dosage,
            note = it.note,
            hour = it.hour,
            minute = it.minute
        )
}