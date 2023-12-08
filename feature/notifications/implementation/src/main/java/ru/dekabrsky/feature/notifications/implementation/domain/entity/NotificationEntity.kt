package ru.dekabrsky.feature.notifications.implementation.domain.entity

import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import java.io.Serializable

@Suppress("LongParameterList")
data class NotificationEntity(
    val uid: Long? = null,
    val tabletName: String = "",
    val dosage: String = "",
    val note: String = "",
    val hour: Int = 0,
    val minute: Int = 0,
    val enabled: Boolean = true,
    val weekDays: List<DayOfWeek> = DayOfWeek.values().toList(),
    val duration: NotificationDurationEntity? = null
) : Serializable

class NotificationDurationEntity(
    val startDate: LocalDate,
    val endDate: LocalDate
): Serializable