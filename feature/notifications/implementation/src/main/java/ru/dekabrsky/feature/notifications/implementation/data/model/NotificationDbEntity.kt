package ru.dekabrsky.feature.notifications.implementation.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.dekabrsky.feature.notifications.implementation.data.converters.DateConverter
import java.util.Date

@Suppress("LongParameterList") // todo я конечно дико извиняюсь, но за что?
@Entity(tableName = NotificationDbEntity.TABLE_NOTIFICATIONS)
class NotificationDbEntity(
    @PrimaryKey val uid: Int? = null,
    val tabletName: String,
    val dosage: String?,
    val note: String?,
    val hour: Int,
    val minute: Int
) {
    companion object {
        const val TABLE_NOTIFICATIONS = "notifications"
    }
}