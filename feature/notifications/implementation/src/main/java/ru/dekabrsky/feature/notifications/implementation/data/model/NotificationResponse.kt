package ru.dekabrsky.feature.notifications.implementation.data.model

import androidx.annotation.Keep

@Keep
@Suppress("LongParameterList")
class NotificationResponse(
    val medicines: List<MedicineResponse>,
    val createdDate: String?,
    val duration: Duration?,
    val enabled: Boolean?,
    val notificationId: Int?,
    val time: String?,
    val weekDays: List<String>?
) {
    @Keep
    data class Duration(
        val endDate: String?,
        val startDate: String?
    )

    @Keep
    class MedicineResponse(
        val name: String?,
        val unit: String?,
        val dosage: String?,
        val note: String?
    )
}