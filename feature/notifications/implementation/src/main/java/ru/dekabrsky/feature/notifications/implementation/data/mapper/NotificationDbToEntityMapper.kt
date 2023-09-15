package ru.dekabrsky.feature.notifications.implementation.data.mapper

import org.threeten.bp.LocalDate
import ru.dekabrsky.feature.notifications.implementation.data.model.NotificationDbEntity
import ru.dekabrsky.feature.notifications.implementation.domain.entity.NotificationEntity
import javax.inject.Inject

class NotificationDbToEntityMapper @Inject constructor() {
    fun dbToEntity(db: NotificationDbEntity): NotificationEntity {
        return NotificationEntity(
            uid = db.uid,
            tabletName = db.tabletName,
            dosage = db.dosage.orEmpty(),
            note = db.note.orEmpty(),
            hour = db.hour,
            minute = db.minute
        )
    }

    fun entityToDb(entity: NotificationEntity): NotificationDbEntity {
        return NotificationDbEntity(
            uid = entity.uid,
            tabletName = entity.tabletName,
            dosage = entity.dosage,
            note = entity.note,
            hour = entity.hour,
            minute = entity.minute
        )
    }
}