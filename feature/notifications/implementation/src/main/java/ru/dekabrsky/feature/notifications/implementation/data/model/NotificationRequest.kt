package ru.dekabrsky.feature.notifications.implementation.data.model

import androidx.annotation.Keep

@Keep
@Suppress("LongParameterList")
class NotificationRequest(
    val medicines: List<MedicineRequest>,
//    val duration: Duration?,
    val enabled: Boolean,
    val time: String,
    val weekDays: List<String>
) {
    //    @Keep
//    data class Duration(
//        val startDate: String,
//        val endDate: String
//    )
    @Keep
    class MedicineRequest(
        val name: String,
        val unit: String,
        val dosage: String,
        val note: String
    )
}