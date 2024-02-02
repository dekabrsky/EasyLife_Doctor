package ru.dekabrsky.feature.notifications.implementation.presentation.mapper

import main.utils.orZero
import ru.dekabrsky.feature.notifications.common.domain.model.NotificationDurationEntity
import ru.dekabrsky.feature.notifications.common.domain.model.NotificationEntity
import ru.dekabrsky.feature.notifications.common.domain.model.NotificationMedicineEntity
import ru.dekabrsky.feature.notifications.implementation.presentation.model.MedicineItemUiModel
import ru.dekabrsky.feature.notifications.implementation.presentation.model.NotificationEditUiModel
import javax.inject.Inject

class NotificationEntityToUiMapper @Inject constructor() {
    fun mapUiToEntity(uiModel: NotificationEditUiModel, id: Long? = null) =
        NotificationEntity(
            uid = id,
            medicines = uiModel.medicines.map { medicine ->
                NotificationMedicineEntity(
                    name = medicine.name,
                    dosage = medicine.dosage,
                    unit = medicine.unit.takeIf { medicine.dosage.isNotBlank() },
                    note = medicine.note
                )
            },
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
            medicines = it.medicines.map {
                MedicineItemUiModel(
                    name = it.name,
                    dosage = it.dosage,
                    note = it.note,
                    unit = it.unit
                )
            }.toMutableList(),
            hour = it.hour,
            minute = it.minute,
            selectedDays = it.weekDays,
            withDuration = it.duration != null,
            startDate = it.duration?.startDate,
            endDate = it.duration?.endDate,
            enabled = it.enabled
        )
}