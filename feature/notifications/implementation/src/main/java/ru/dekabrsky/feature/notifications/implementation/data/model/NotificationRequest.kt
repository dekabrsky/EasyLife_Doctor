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
    val time: Time,
    val weekDays: List<String>
) {
    @Keep
    data class Duration(
        val endDate: String,
        val startDate: String
    )

    @Keep
    data class Time(
        val hour: Int,
        val minute: Int
    )
}