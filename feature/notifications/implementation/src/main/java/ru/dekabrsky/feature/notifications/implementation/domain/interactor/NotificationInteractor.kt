package ru.dekabrsky.feature.notifications.implementation.domain.interactor

import main.utils.orZero
import ru.dekabrsky.feature.notifications.implementation.data.repository.NotificationApiRepository
import ru.dekabrsky.feature.notifications.implementation.data.repository.NotificationRepository
import ru.dekabrsky.feature.notifications.implementation.domain.entity.NotificationEntity
import javax.inject.Inject

class NotificationInteractor @Inject constructor(
    private val repository: NotificationApiRepository
) {
    fun getAll() = repository.getAll()

    fun add(notification: NotificationEntity) = repository.add(notification)

    fun delete(notification: NotificationEntity) = repository.delete(notification.uid?.toInt().orZero())

    fun update(notification: NotificationEntity) = repository.update(notification)
}