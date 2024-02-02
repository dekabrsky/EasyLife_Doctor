package ru.dekabrsky.feature.notifications.implementation.data.mapper

import main.utils.isTrue
import org.threeten.bp.DayOfWeek
import ru.dekabrsky.feature.notifications.common.domain.model.DosageUnit
import ru.dekabrsky.feature.notifications.common.domain.model.NotificationDurationEntity
import ru.dekabrsky.feature.notifications.common.domain.model.NotificationEntity
import ru.dekabrsky.feature.notifications.common.domain.model.NotificationMedicineEntity
import ru.dekabrsky.feature.notifications.implementation.data.model.NotificationDbEntity
import ru.dekabrsky.feature.notifications.implementation.data.model.NotificationRequest
import ru.dekabrsky.feature.notifications.implementation.data.model.NotificationResponse
import ru.dekabrsky.italks.basic.dateTime.formatDateToServerString
import ru.dekabrsky.italks.basic.dateTime.formatHourAndMinute
import ru.dekabrsky.italks.basic.dateTime.hourAndMinuteFromString
import ru.dekabrsky.italks.basic.dateTime.tryParseServerDate
import javax.inject.Inject

class NotificationDbToEntityMapper @Inject constructor() {
    fun dbToEntity(db: NotificationDbEntity): NotificationEntity {
        val (hour, minute) = hourAndMinuteFromString(db.time)
        return NotificationEntity(
            uid = db.uid,
//            medicines = db.medicines.map {
//                NotificationMedicineEntity(
//                    name = it.name.orEmpty(),
//                    unit = DosageUnit.getByValue(it.unit),
//                    dosage = it.dosage.orEmpty(),
//                    note = it.note.orEmpty(),
//                )
//            },
            hour = hour,
            minute = minute,
            enabled = db.enabled.isTrue(),
            weekDays = db.weekDays?.map { DayOfWeek.valueOf(it) } ?: DayOfWeek.values().toList(),
            duration = db.endDate?.let { end ->
                tryParseServerDate(end)?.let { endDate ->
                    db.startDate?.let { start ->
                        tryParseServerDate(start)?.let { startDate ->
                            NotificationDurationEntity(startDate, endDate)
                        }
                    }
                }
            }
        )
    }

    fun entityToDb(entity: NotificationEntity): NotificationDbEntity {
        return NotificationDbEntity(
            uid = entity.uid,
//            medicines = entity.medicines.map {
//                NotificationResponse.MedicineResponse(
//                    name = it.name,
//                    unit = it.unit.toString(),
//                    dosage = it.dosage,
//                    note = it.note
//                )
//            },
            time = formatHourAndMinute(entity.hour, entity.minute),
            enabled = entity.enabled,
            weekDays = entity.weekDays.map { it.name },
            startDate = entity.duration?.startDate?.let { formatDateToServerString(it) }.orEmpty(),
            endDate = entity.duration?.endDate?.let { formatDateToServerString(it) }.orEmpty()
        )
    }
}