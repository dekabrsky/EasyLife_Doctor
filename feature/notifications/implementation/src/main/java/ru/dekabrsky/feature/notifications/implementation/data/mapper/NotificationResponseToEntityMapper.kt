package ru.dekabrsky.feature.notifications.implementation.data.mapper

import main.utils.isTrue
import main.utils.orZero
import org.threeten.bp.DayOfWeek
import ru.dekabrsky.easylife.basic.dateTime.formatHourAndMinute
import ru.dekabrsky.easylife.basic.dateTime.hourAndMinuteFromString
import ru.dekabrsky.easylife.basic.dateTime.tryParseServerDate
import ru.dekabrsky.feature.notifications.common.domain.model.DosageUnit
import ru.dekabrsky.feature.notifications.common.domain.model.NotificationDurationEntity
import ru.dekabrsky.feature.notifications.common.domain.model.NotificationEntity
import ru.dekabrsky.feature.notifications.common.domain.model.NotificationMedicineEntity
import ru.dekabrsky.feature.notifications.implementation.data.model.NotificationRequest
import ru.dekabrsky.feature.notifications.implementation.data.model.NotificationResponse
import javax.inject.Inject

class NotificationResponseToEntityMapper @Inject constructor() {
    fun mapResponseToEntity(response: NotificationResponse): NotificationEntity {
        val (hour, minute) = hourAndMinuteFromString(response.time.orEmpty())
        return NotificationEntity(
            uid = response.notificationId.orZero().toLong(),
            medicines = response.medicines.map {
                NotificationMedicineEntity(
                    name = it.name.orEmpty(),
                    unit = DosageUnit.getByValue(it.unit),
                    dosage = it.dosage.orEmpty(),
                    note = it.note.orEmpty(),
                )
            },
            hour = hour,
            minute = minute,
            enabled = response.enabled.isTrue(),
            weekDays = response.weekDays?.map { DayOfWeek.valueOf(it) } ?: DayOfWeek.values().toList(),
            duration = response.duration?.let { duration ->
                duration.endDate?.let { end ->
                    tryParseServerDate(end)?.let { endDate ->
                        duration.startDate?.let { start ->
                            tryParseServerDate(start)?.let { startDate ->
                                NotificationDurationEntity(startDate, endDate)
                            }
                        }
                    }
                }
            }
        )
    }

    fun mapEntityToRequest(entity: NotificationEntity): NotificationRequest {
        return NotificationRequest(
            medicines = entity.medicines.map {
                NotificationRequest.MedicineRequest(
                    name = it.name,
                    unit = it.unit?.toString().orEmpty(),
                    dosage = it.dosage,
                    note = it.note
                )
            },
            time = formatHourAndMinute(entity.hour, entity.minute, withSeconds = true),
            enabled = entity.enabled,
            weekDays = entity.weekDays.map { it.name },
//            duration = entity.duration?.startDate?.let { formatDateToServerString(it) }?.let { start ->
//                entity.duration?.endDate?.let { formatDateToServerString(it) }.let { end ->
//                    NotificationRequest.Duration(start, end)
//                }
//            }
        )
    }
}