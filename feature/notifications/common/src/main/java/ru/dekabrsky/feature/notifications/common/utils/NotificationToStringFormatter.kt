package ru.dekabrsky.feature.notifications.common.utils

import main.utils.orZero
import ru.dekabrsky.easylife.basic.resources.ResourceProvider
import ru.dekabrsky.feature.notifications.common.domain.model.NotificationEntity
import ru.dekabrsky.feature.notifications.common.domain.model.NotificationMedicineEntity
import javax.inject.Inject

class NotificationToStringFormatter @Inject constructor(
    private val resourceProvider: ResourceProvider
) {
    fun formatMedicines(notification: NotificationEntity) =
        notification.medicines.joinToString("\n") {
            listOf(
                it.name,
                formatDosage(it),
                it.note
            )
                .filter { it.isNotBlank() }
                .joinToString()
        }

    fun formatDosage(medicine: NotificationMedicineEntity) =
        medicine.dosage.toIntOrNull()?.let {
            "${medicine.dosage} ${
                medicine.unit?.let { unit ->
                    resourceProvider.getQuantityString(
                        unit.rusUnitName,
                        medicine.dosage.toIntOrNull().orZero()
                    )
                }.orEmpty()
            }"
        }.orEmpty()
}