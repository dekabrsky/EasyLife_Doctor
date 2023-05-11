package ru.dekabrsky.feature.notifications.implementation.presentation.model

class NotificationItemUiModel(
    val id: Int,
    val time: String,
    val name: String,
    val dosage: String,
    val note: String,
    val enabled: Boolean
)