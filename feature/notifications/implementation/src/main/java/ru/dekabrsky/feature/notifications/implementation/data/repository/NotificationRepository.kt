package ru.dekabrsky.feature.notifications.implementation.data.repository

import androidx.room.RoomDatabase
import ru.dekabrsky.feature.notifications.common.domain.model.NotificationEntity
import ru.dekabrsky.feature.notifications.implementation.data.database.NotificationDatabase
import ru.dekabrsky.feature.notifications.implementation.data.mapper.NotificationDbToEntityMapper
import ru.dekabrsky.italks.basic.di.NotificationDatabaseQualifier
import javax.inject.Inject

class NotificationRepository @Inject constructor(
    @NotificationDatabaseQualifier private val roomDatabase: RoomDatabase,
    private val mapper: NotificationDbToEntityMapper
) {
    private val notificationDatabase = roomDatabase as NotificationDatabase
    private val dao = notificationDatabase.notificationsDao()

    fun getAll() = dao.getAll().map { list -> list.map { mapper.dbToEntity(it) } }

    fun add(notification: NotificationEntity) = dao.insert(mapper.entityToDb(notification))

    fun delete(notification: NotificationEntity) = dao.delete(mapper.entityToDb(notification))

    fun update(notification: NotificationEntity) = dao.update(mapper.entityToDb(notification))

}