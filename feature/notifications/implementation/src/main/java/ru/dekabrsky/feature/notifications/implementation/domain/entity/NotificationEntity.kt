package ru.dekabrsky.feature.notifications.implementation.domain.entity

import androidx.room.PrimaryKey
import org.threeten.bp.LocalDate
import java.util.Date

@Suppress("LongParameterList")
class NotificationEntity(
    val uid: Int? = null,
    val tabletName: String = "",
    val dosage: String = "",
    val note: String = "",
    val hour: Int = 0,
    val minute: Int = 0
)