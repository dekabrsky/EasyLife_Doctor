package ru.dekabrsky.feature.notifications.implementation.domain.interactor

import ru.dekabrsky.feature.notifications.implementation.data.repository.NotificationRepository
import ru.dekabrsky.feature.notifications.implementation.domain.entity.NotificationEntity
import javax.inject.Inject

class NotificationInteractor @Inject constructor(
    private val repository: NotificationRepository
) {
    fun getAll() = repository.getAll()

    fun add(notification: NotificationEntity) = repository.add(notification)
}