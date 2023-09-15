package ru.dekabrsky.feature.notifications.implementation.presentation.model

class NotificationEditUiModel(
    var tabletName: String = "",
    var dosage: String = "",
    var note: String = "",
    var hour: Int = 0,
    var minute: Int = 0
) {
    val time: String
        get() {
            val hour = if (hour < MIN_TWO_DIGIT_MINUTE) "0$hour" else hour.toString()
            val minute = if (minute < MIN_TWO_DIGIT_MINUTE) "0$minute" else minute.toString()
            return "$hour:$minute"
        }

    companion object {
        private const val MIN_TWO_DIGIT_MINUTE = 10
    }
}