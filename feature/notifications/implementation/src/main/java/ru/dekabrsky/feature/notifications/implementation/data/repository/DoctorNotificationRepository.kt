package ru.dekabrsky.feature.notifications.implementation.data.repository

import main.utils.orZero
import ru.dekabrsky.feature.notifications.common.domain.model.NotificationEntity
import ru.dekabrsky.feature.notifications.implementation.data.api.DoctorNotificationApi
import ru.dekabrsky.feature.notifications.implementation.data.api.NotificationApi
import ru.dekabrsky.feature.notifications.implementation.data.mapper.NotificationResponseToEntityMapper
import javax.inject.Inject

class DoctorNotificationRepository @Inject constructor(
    private val api: DoctorNotificationApi,
    private val mapper: NotificationResponseToEntityMapper
) {

    fun getAll(userId: Long) =
        api.getNotifications(userId)
            .map { list ->
                list
                    .sortedBy { it.time }
                    .map { mapper.mapResponseToEntity(it) }
            }

    fun add(userId: Long, notification: NotificationEntity) =
        api.postNotification(mapper.mapEntityToRequest(notification), userId)

    fun delete(userId: Long, notificationId: Long) = api.deleteNotification(notificationId, userId)

    fun update(userId: Long, notification: NotificationEntity) =
        api.putNotification(
            id = notification.uid.orZero(),
            userId = userId,
            request = mapper.mapEntityToRequest(notification)
        )

}