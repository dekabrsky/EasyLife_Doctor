package ru.dekabrsky.feature.notifications.implementation.presentation.model

import java.io.Serializable

class NotificationsSharedPreferencesItems(
    var uidList: MutableSet<Long> = mutableSetOf()
): Serializable