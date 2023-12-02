package ru.dekabrsky.feature.notifications.implementation.presentation.mapper

import main.utils.orZero
import ru.dekabrsky.feature.notifications.implementation.domain.entity.NotificationDurationEntity
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
            hour = uiModel.hour.orZero(),
            minute = uiModel.minute.orZero(),
            enabled = uiModel.enabled,
            weekDays = uiModel.selectedDays,
            duration = uiModel.startDate?.let { start ->
                uiModel.endDate?.let { end ->
                    NotificationDurationEntity(start, end)
                }
            }
        )

    fun mapEntityToUi(it: NotificationEntity) =
        NotificationEditUiModel(
            tabletName = it.tabletName,
            dosage = it.dosage,
            note = it.note,
            hour = it.hour,
            minute = it.minute,
            selectedDays = it.weekDays,
            withDuration = it.duration != null,
            startDate = it.duration?.startDate,
            endDate = it.duration?.endDate,
            enabled = it.enabled
        )
}