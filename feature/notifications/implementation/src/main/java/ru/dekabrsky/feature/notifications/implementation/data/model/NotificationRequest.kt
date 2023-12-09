package ru.dekabrsky.feature.notifications.implementation.data.model

import androidx.annotation.Keep

@Keep
@Suppress("LongParameterList")
class NotificationRequest(
    val dosage: String,
    val duration: Duration?,
    val enabled: Boolean,
    val name: String,
    val note: String,
    val time: String,
    val weekDays: List<String>
) {
    @Keep
    data class Duration(
        val startDate: String,
        val endDate: String
    )
}