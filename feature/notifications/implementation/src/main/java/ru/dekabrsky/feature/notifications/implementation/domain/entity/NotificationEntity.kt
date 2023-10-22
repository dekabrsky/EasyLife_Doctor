package ru.dekabrsky.feature.notifications.implementation.domain.entity

@Suppress("LongParameterList")
class NotificationEntity(
    val uid: Long? = null,
    val tabletName: String = "",
    val dosage: String = "",
    val note: String = "",
    val hour: Int = 0,
    val minute: Int = 0
)