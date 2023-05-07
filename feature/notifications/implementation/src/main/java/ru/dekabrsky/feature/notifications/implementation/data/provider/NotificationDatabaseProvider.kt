package ru.dekabrsky.feature.notifications.implementation.data.provider

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.dekabrsky.feature.notifications.implementation.data.database.NotificationDatabase
import ru.dekabrsky.feature.notifications.implementation.data.model.NotificationDbEntity
import javax.inject.Inject
import javax.inject.Provider

class NotificationDatabaseProvider @Inject constructor(
    private val context: Context
) : Provider<RoomDatabase> {
    override fun get(): RoomDatabase {
        return Room.databaseBuilder(
            context,
            NotificationDatabase::class.java, NotificationDbEntity.TABLE_NOTIFICATIONS
        )
            .allowMainThreadQueries()
            .build()
    }
}