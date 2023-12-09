package ru.dekabrsky.feature.notifications.implementation.data.model

import androidx.annotation.Keep

@Keep
@Suppress("LongParameterList")
class NotificationResponse(
    val createdDate: String?,
    val dosage: String?,
    val duration: Duration?,
    val enabled: Boolean?,
    val name: String?,
    val note: String?,
    val notificationId: Int?,
    val time: String?,
    val weekDays: List<String>?
) {
    @Keep
    data class Duration(
        val endDate: String?,
        val startDate: String?
    )
}