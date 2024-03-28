package ru.dekabrsky.feature.notifications.common.presentation.model

import java.io.Serializable

class NotificationsFlowArgs(
    val parentScopeName: String,
    val patientId: Long? = null,
    val patientName: String? = null
): Serializable