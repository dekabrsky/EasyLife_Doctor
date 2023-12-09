package ru.dekabrsky.feature.notifications.implementation.data.repository

import main.utils.orZero
import ru.dekabrsky.feature.notifications.implementation.data.api.NotificationApi
import ru.dekabrsky.feature.notifications.implementation.data.mapper.NotificationResponseToEntityMapper
import ru.dekabrsky.feature.notifications.implementation.domain.entity.NotificationEntity
import javax.inject.Inject

class NotificationApiRepository @Inject constructor(
    private val api: NotificationApi,
    private val mapper: NotificationResponseToEntityMapper
) {
    fun getAll() = api.getNotifications().map { list -> list.map { mapper.mapResponseToEntity(it) } }

    fun add(notification: NotificationEntity) =
        api
            .postNotification(mapper.mapEntityToRequest(notification))
            .map { mapper.mapResponseToEntity(it) }

    fun delete(notificationId: Int) = api.deleteNotification(notificationId)

    fun update(notification: NotificationEntity) =
        api.putNotification(notification.uid?.toInt().orZero(), mapper.mapEntityToRequest(notification))

}