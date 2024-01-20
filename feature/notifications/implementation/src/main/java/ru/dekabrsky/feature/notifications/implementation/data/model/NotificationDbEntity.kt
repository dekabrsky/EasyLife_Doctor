package ru.dekabrsky.feature.notifications.implementation.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Suppress("LongParameterList") // todo я конечно дико извиняюсь, но за что?
@Entity(tableName = NotificationDbEntity.TABLE_NOTIFICATIONS)
class NotificationDbEntity(
    @PrimaryKey val uid: Long? = null,
    val tabletName: String,
    val dosage: String?,
    val note: String?,
    val time: String?,
    val enabled: Boolean?,
    val weekDays: List<String>?,
    val startDate: String?,
    val endDate: String?
) {
    companion object {
        const val TABLE_NOTIFICATIONS = "notifications"
    }
}