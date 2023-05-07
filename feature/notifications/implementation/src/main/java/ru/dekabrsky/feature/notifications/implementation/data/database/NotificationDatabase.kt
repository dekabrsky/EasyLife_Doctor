package ru.dekabrsky.feature.notifications.implementation.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.dekabrsky.feature.notifications.implementation.data.converters.DateConverter
import ru.dekabrsky.feature.notifications.implementation.data.dao.NotificationDao
import ru.dekabrsky.feature.notifications.implementation.data.model.NotificationDbEntity

@Database(entities = [NotificationDbEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class NotificationDatabase: RoomDatabase() {
    abstract fun notificationsDao(): NotificationDao
}