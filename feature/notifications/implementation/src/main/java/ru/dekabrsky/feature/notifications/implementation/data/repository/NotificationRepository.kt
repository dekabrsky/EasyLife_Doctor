package ru.dekabrsky.feature.notifications.implementation.data.repository

import androidx.room.RoomDatabase
import ru.dekabrsky.feature.notifications.implementation.data.database.NotificationDatabase
import ru.dekabrsky.feature.notifications.implementation.data.mapper.NotificationDbToEntityMapper
import ru.dekabrsky.feature.notifications.implementation.domain.entity.NotificationEntity
import ru.dekabrsky.italks.basic.di.NotificationDatabaseQualifier
import javax.inject.Inject

class NotificationRepository @Inject constructor(
    @NotificationDatabaseQualifier private val roomDatabase: RoomDatabase,
    private val mapper: NotificationDbToEntityMapper
) {
    private val notificationDatabase = roomDatabase as NotificationDatabase

    fun getAll() =
        notificationDatabase.notificationsDao().getAll()
            .map { list -> list.map { mapper.dbToEntity(it) } }

    fun add(notification: NotificationEntity) =
        notificationDatabase.notificationsDao().insert(mapper.entityToDb(notification))
}