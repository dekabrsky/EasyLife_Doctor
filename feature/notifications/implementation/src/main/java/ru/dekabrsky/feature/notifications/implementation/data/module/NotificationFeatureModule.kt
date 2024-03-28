package ru.dekabrsky.feature.notifications.implementation.data.module

import ru.dekabrsky.feature.notifications.implementation.data.api.DoctorNotificationApi
import ru.dekabrsky.feature.notifications.implementation.data.api.NotificationApi
import ru.dekabrsky.feature.notifications.implementation.data.provider.DoctorNotificationApiProvider
import ru.dekabrsky.feature.notifications.implementation.data.provider.NotificationApiProvider
import toothpick.config.Module

class NotificationFeatureModule : Module() {
    init {
        bind(NotificationApi::class.java).toProvider(NotificationApiProvider::class.java)
        bind(DoctorNotificationApi::class.java).toProvider(DoctorNotificationApiProvider::class.java)
    }
}
