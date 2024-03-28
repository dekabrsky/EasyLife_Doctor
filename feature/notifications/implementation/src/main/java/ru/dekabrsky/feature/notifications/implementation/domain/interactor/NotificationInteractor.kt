package ru.dekabrsky.feature.notifications.implementation.domain.interactor

import io.reactivex.Completable
import io.reactivex.Single
import main.utils.orZero
import ru.dekabrsky.feature.notifications.common.domain.model.NotificationEntity
import ru.dekabrsky.feature.notifications.implementation.data.repository.NotificationApiRepository
import javax.inject.Inject

class NotificationInteractor @Inject constructor(
    private val repository: NotificationApiRepository
): INotificationInteractor {
    override fun getAll(): Single<List<NotificationEntity>> = repository.getAll()

    override fun add(notification: NotificationEntity): Completable = repository.add(notification)

    override fun delete(notification: NotificationEntity): Completable = repository.delete(notification.uid.orZero())

    override fun update(notification: NotificationEntity): Completable = repository.update(notification)
}